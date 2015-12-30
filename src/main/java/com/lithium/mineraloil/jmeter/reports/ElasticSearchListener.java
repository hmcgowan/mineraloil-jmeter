package com.lithium.mineraloil.jmeter.reports;


import lithium.analytics.classifier.WurflClassifier;
import lithium.datainv.classifier.PageNameClassifier;
import lithium.util.Config;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.backend.AbstractBackendListenerClient;
import org.apache.jmeter.visualizers.backend.BackendListenerContext;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by viren.thakkar on 11/30/15.
 */
public class ElasticSearchListener extends AbstractBackendListenerClient {
    protected final Logger log = LoggerFactory.getLogger(ElasticSearchListener.class);
    private int DEFAULT_ELASTICSEARCH_PORT = 9300;
    private String ELASTICSEARCH_HOST = "localhost";
    private String INDEX_NAME;
    private String indexName;
    private String TestRunID;
    private TransportClient client;
    private String dateTimeAppendFormat;
    WurflClassifier wurflClassifier;

    @Override
    public void handleSampleResults(List<SampleResult> results,
                                    BackendListenerContext context) {
        String indexNameToUse = indexName;
        for (SampleResult result : results) {
            Map<String, Object> jsonObject = getMap(result);
            if (dateTimeAppendFormat != null) {
                Calendar currentDate = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat(dateTimeAppendFormat);
                indexNameToUse = indexName + sdf.format(currentDate.getTime());
            }
            boolean indexExists = client.admin().indices().prepareExists(indexNameToUse).execute().actionGet().isExists();
            if (!indexExists) {
                Settings indexSettings = Settings.settingsBuilder()
                        .put("number_of_shards", 1)
                        .put("number_of_replicas", 1)
                        .build();
                client.admin().indices().prepareCreate(indexNameToUse).setSettings(indexSettings).execute().actionGet();
            }

            IndexResponse response =client.prepareIndex(indexNameToUse, "SampleResult").setSource(jsonObject).get();
            log.info("Document created :{}",response.isCreated());

        }
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("elasticsearchCluster", ELASTICSEARCH_HOST + ":" + DEFAULT_ELASTICSEARCH_PORT);
        arguments.addArgument("indexName", "jmeter_v1");
        arguments.addArgument("sampleType", "SampleResult");
        arguments.addArgument("dateTimeAppendFormat", "-yyyy-MM ");
        arguments.addArgument("normalizedTime", "2015-01-01 00:00:00.000-00:00");
        arguments.addArgument("runId", TestRunID);
        return arguments;
    }

    @Override
    public void setupTest(BackendListenerContext context) throws Exception {
        String elasticsearchCluster = context.getParameter("elasticsearchCluster");
        String[] servers = elasticsearchCluster.split(",");

        indexName = context.getParameter("indexName");
        TestRunID = context.getParameter("runId");
        dateTimeAppendFormat = context.getParameter("dateTimeAppendFormat");
        if (dateTimeAppendFormat != null && dateTimeAppendFormat.trim().equals("")) {
            dateTimeAppendFormat = null;
        }
        String sampleType = context.getParameter("sampleType");
        String esHost = context.getParameter("elasticsearchCluster");
        String port = context.getParameter("elasticsearchPort");
        String clusterName = context.getParameter("clusterName");
        Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).put("transport.tcp.connect_timeout", "30s").build();

        //Add transport addresses and do something with the client...
        client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost), Integer.parseInt(port)));
        //client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost), Integer.parseInt(port)));

        //snip
        super.setupTest(context);

        // Setup device classification

        addDeviceClassification();
    }

    private Map<String, Object> getMap(SampleResult result) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("RunId", JMeterUtils.getPropDefault("testRun","Unknown"));
        map.put("ThreadName", result.getThreadName());
        map.put("timestamp", result.getTimeStamp());
        map.put("ResponseTime", result.getTime());
        map.put("ResponseCode", result.getResponseCode());
        map.put("URL", result.getUrlAsString());
        map.put("Success", result.isSuccessful());
        map.put("SampleCount", result.getSampleCount());
        map.put("Bytes", result.getBytes());
        map.put("ElapsedTime", result.getEndTime() - result.getStartTime());
        map.put("Latency", result.getLatency());
        map.put("ConnectTime", result.getConnectTime());
        map.put("ErrorCount", result.getErrorCount());
        map.put("StartTime", result.getStartTime());
        map.put("EndTime", result.getEndTime());
        map.put("release", JMeterUtils.getPropDefault("release","Unknown"));
        map.put("revision", JMeterUtils.getPropDefault("revision", "Unknown"));
        map.put("community", JMeterUtils.getPropDefault("community", "Unknown"));


        Config config = new lithium.util.Config();
        config.put("tapestry.context.name", "t5");
        PageNameClassifier pageNameClassifier = new PageNameClassifier(config);
        String pageName = pageNameClassifier.classify(result.getURL().getPath());

        if (wurflClassifier.classify(getUserAgent(result.getRequestHeaders())).equalsIgnoreCase("mobile")) {
            map.put("device", "mobile");
            map.put("SampleLabel", "Mobile" + pageName);
        } else if ((wurflClassifier.classify(getUserAgent(result.getRequestHeaders())).equalsIgnoreCase("crawler"))) {
            map.put("device", "bot");
            map.put("SampleLabel", pageName);
        } else {
            map.put("device", "desktop");
            map.put("SampleLabel", pageName);

        }


        //snip lots more code
        return map;
    }

    public void addDeviceClassification() {

        lithium.util.Config config = new lithium.util.Config();
        config.put("wurfl.cache.user_agent.size", "10000");
        config.put("wurfl.fileName", "wurfl.gz");
        config.put("wurfl.compressType", "gz");

        wurflClassifier = new WurflClassifier(config);

    }


    @Override
    public void teardownTest(BackendListenerContext context) throws Exception {
        client.close();
        super.teardownTest(context);
    }

    private String getUserAgent(String requestHeaders) {

        String[] headers = requestHeaders.split("\n");

        for (String header : headers) {
            if (header.equalsIgnoreCase("User-Agent")) {

                return header;
            }
        }

        return null;
    }

}
