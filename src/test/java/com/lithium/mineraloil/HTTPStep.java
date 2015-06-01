package com.lithium.mineraloil;

import com.lithium.mineraloil.jmeter.JMeterRunner;
import com.lithium.mineraloil.jmeter.test_elements.HTTPSamplerElement;
import com.lithium.mineraloil.jmeter.test_elements.ThreadGroupElement;
import org.junit.Test;

public class HTTPStep {

    @Test
    public void httpRequest() {
        // TODO: add a simple server
        // In the meanttime, to run this start a simple server with something like: python -m SimpleHTTPServer
        // There's no verification here but you can see the output in the console for the server to get a few requests

        HTTPSamplerElement login = HTTPSamplerElement.builder()
                                                     .domain("0.0.0.0")
                                                     .port(8000)
                                                     .protocol("http")
                                                     .path("/")
                                                     .method("GET")
                                                     .build();
        JMeterRunner jmeter = new JMeterRunner();
        ThreadGroupElement threadGroup = ThreadGroupElement.builder()
                                                           .loopCount(2)
                                                           .name("Show Active Users Test").build();
        threadGroup.addReportableStep(login);
        jmeter.addStep(threadGroup);
        jmeter.run();
    }
}
