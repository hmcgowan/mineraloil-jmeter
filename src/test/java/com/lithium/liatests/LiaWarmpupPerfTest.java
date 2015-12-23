package com.lithium.liatests;

import com.lithium.mineraloil.jmeter.JMeterRunner;
import com.lithium.mineraloil.jmeter.test_elements.CSVDataSetElement;
import com.lithium.mineraloil.jmeter.test_elements.HTTPSamplerElement;
import com.lithium.mineraloil.jmeter.test_elements.ThreadGroupElement;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by viren.thakkar on 12/23/15.
 */
public class LiaWarmpupPerfTest {

    @Test
    public void remoteTest(){
        /*
          Get all vm arguments here.
         */
        String protocol = System.getProperty("protocol", "http");
        int port = Integer.parseInt(System.getProperty("port", "80"));
        String domain = System.getProperty("host");
        String urlPath = System.getProperty("path", "/");
        int threads = Integer.parseInt(System.getProperty("users"));
        int rampup = Integer.parseInt(System.getProperty("rampup"));
        String remoteJmeterInstance = System.getProperty("remoteJmeterHost");
        String csvFile = System.getProperty("csvFileLocation");
     /*   String elasticSearchCluster = System.getProperty("elasticSearchClusterName");
        String elasticSearchHost = System.getProperty("elasticSearchHost");
        int elasticSearchPort = Integer.parseInt(System.getProperty("elasticSearchPort"));
        String testRun = System.getProperty("testRun");
        String release = System.getProperty("release");
        String revision = System.getProperty("revision");
        String community = System.getProperty("community");

        /*
          This is to pass extra parameters (JmeterProperties) to jmeter.
         */
       /* Properties properties = new Properties();
        properties.put("elasticSearchClusterName",elasticSearchCluster);
        properties.put("elasticSearchHost",elasticSearchHost);
        properties.put("elasticSearchPort",String.valueOf(elasticSearchPort));
        properties.put("testRun",testRun);
        properties.put("release",release);
        properties.put("revision",revision);
        properties.put("community",community);*/

        JMeterRunner jmeter = new JMeterRunner("httpRequest");
       // jmeter.addExtraJmeterProperties(properties);

        /* Create header element */
        HeaderManager headerManager = new HeaderManager();
        Header header = new Header("User-Agent", "${useragent}");
        headerManager.add(header);

        /* Create HttpSampler Object */
        HTTPSamplerElement login = HTTPSamplerElement.builder()
                .domain(domain)
                .port(port)
                .protocol(protocol)
                .path("${url}")
                .method("GET")
                .implementation("HttpClient4").headerManager(headerManager)
                .build();

        ThreadGroupElement threadGroup = ThreadGroupElement.builder().threadCount(threads).rampUp(rampup)
                .continueForever(true).setScheduler(true).duration(Integer.parseInt(System.getProperty("duration", "10")))
                .name("Show Active Users Test").build();
        CSVDataSetElement csvDataSetElement = CSVDataSetElement.builder().
                fileName(csvFile).
                delimiter("#").variableNames("useragent,url").name("allurls").quotedData(false).stopThread(true).shareMode("shareMode.all")
                .recycle(true).build();
        threadGroup.addStep(csvDataSetElement);

        threadGroup.addReportableStep(login);
        jmeter.addStep(threadGroup);

       // jmeter.addElasticSearchMapping(elasticSearchCluster, elasticSearchHost, elasticSearchPort);
        //jmeter.addElasticSearchListener(elasticSearchCluster, testRun, elasticSearchHost, elasticSearchPort);

        /* Now run the jmeter client which will run script on remote instance */
        List<String> remoteHosts= Arrays.asList(remoteJmeterInstance.split(","));

        jmeter.remoteRun(remoteHosts);
    }
}
