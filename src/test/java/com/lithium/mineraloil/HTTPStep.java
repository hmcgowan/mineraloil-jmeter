package com.lithium.mineraloil;

import com.lithium.mineraloil.jmeter.JMeterRunner;
import com.lithium.mineraloil.jmeter.test_elements.CSVDataSetElement;
import com.lithium.mineraloil.jmeter.test_elements.HTTPSamplerElement;
import com.lithium.mineraloil.jmeter.test_elements.ThreadGroupElement;
import org.apache.jmeter.engine.RemoteJMeterEngineImpl;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class HTTPStep {


    @Test
    public void httpRequest() {
        // TODO: add a simple server
        // In the meanttime, to run this start a simple server with something like: python -m SimpleHTTPServer
        // There's no verification here but you can see the output in the console for the server to get a few requests


        HTTPSamplerElement login = HTTPSamplerElement.builder()
                .domain("jenkins.performance.qa.lithium.com")
                .port(80)
                .protocol("http")
                .path("/")
                .method("GET")
                .build();
        JMeterRunner jmeter = new JMeterRunner("httpRequest");
        ThreadGroupElement threadGroup = ThreadGroupElement.builder()
                .loopCount(2)
                .name("Show Active Users Test").build();
        threadGroup.addReportableStep(login);
        jmeter.addStep(threadGroup);
        jmeter.run();
    }


    @Test
    public void httpRequestRemoteRun() {

        String protocol = System.getProperty("protocol", "http");
        int port = Integer.parseInt(System.getProperty("port", "80"));
        String domain = System.getProperty("host");
        String urlPath = System.getProperty("path", "/");
        int threads = Integer.parseInt(System.getProperty("users"));
        int rampup = Integer.parseInt(System.getProperty("rampup"));
        String remoteJmeterInstance = System.getProperty("remoteJmeterHost");
        String csvFile = System.getProperty("csvFileLocation");
        String elasticSearchCluster = System.getProperty("elasticSearchClusterName");
        String elasticSearchHost = System.getProperty("elasticSearchHost");
        int elasticSearchPort = Integer.parseInt(System.getProperty("elasticSearchPort"));
        String testRun = System.getProperty("testRun");
        String release = System.getProperty("release");
        String revision = System.getProperty("revision");
        String community = System.getProperty("community");

        Properties properties = new Properties();
        properties.put("elasticSearchClusterName", elasticSearchCluster);
        properties.put("elasticSearchHost", elasticSearchHost);
        properties.put("elasticSearchPort", String.valueOf(elasticSearchPort));
        properties.put("testRun", testRun);
        properties.put("release", release);
        properties.put("revision", revision);
        properties.put("community", community);

        JMeterRunner jmeter = new JMeterRunner("httpRequest");
        jmeter.addExtraJmeterProperties(properties);
        /*  Here first i am starting remote jmeter instance */
        try {

            RemoteJMeterEngineImpl.startServer(0);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        /* Get the ip of remote jmeter instance. Which is here localhost */

        InetAddress ip = null;
        String hostname;
        try {
            ip = InetAddress.getByName(remoteJmeterInstance);
            hostname = ip.getHostName();
        } catch (UnknownHostException e) {

            e.printStackTrace();
        }


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

        jmeter.addElasticSearchMapping(elasticSearchCluster, elasticSearchHost, elasticSearchPort);
        jmeter.addElasticSearchListener(elasticSearchCluster, testRun, elasticSearchHost, elasticSearchPort);

        /* Now run the jmeter client which will run script on remote instance */
        /* Now run the jmeter client which will run script on remote instance */
        List<String> remoteHosts= Arrays.asList(remoteJmeterInstance.split(","));
        jmeter.remoteRun(remoteHosts);

    }
}
