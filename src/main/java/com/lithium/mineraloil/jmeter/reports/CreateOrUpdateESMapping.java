package com.lithium.mineraloil.jmeter.reports;

import com.lithium.mineraloil.jmeter.JMeterRunner;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesResponse;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.IndexTemplateMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

//import org.elasticsearch.common.settings.ImmutableSettings;

/**
 * Created by viren.thakkar on 12/14/15.
 */
public class CreateOrUpdateESMapping {

    private TransportClient client;
    private String JMETER_TEMPLATE_V1 = "jmeter_template_v1";

    public CreateOrUpdateESMapping(String clusterName, String host, int port) {

        Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).put("transport.tcp.connect_timeout", "30s").build();
        try {
            client = TransportClient.builder().settings(settings).build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
            //client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Boolean createMapping() {
        String elasticSearchDir = JMeterRunner.class.getClassLoader().getResource("elasticsearch").getPath();
        File mapping = new File(elasticSearchDir + "/jmeter_mapping_v1");

        GetIndexTemplatesResponse response = client.admin().indices().prepareGetTemplates().get();
        Boolean templateExist = false;

        for (IndexTemplateMetaData templateMetaData : response.getIndexTemplates()) {
            if (templateMetaData.getName().equalsIgnoreCase(JMETER_TEMPLATE_V1)) {
                templateExist = true;
                return true;
            }
        }
        PutIndexTemplateResponse putIndexTemplateResponse = null;
        if (!templateExist) {
            try {
                putIndexTemplateResponse = client.admin().indices().preparePutTemplate(JMETER_TEMPLATE_V1).setTemplate("jmeter_v1*").setOrder(0).addMapping("SampleResult", FileUtils.readFileToString(mapping).trim()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (putIndexTemplateResponse != null && putIndexTemplateResponse.isAcknowledged())
            return true;
        else
            return false;

    }


}
