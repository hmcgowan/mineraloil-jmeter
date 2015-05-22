package com.lithium.mineraloil.jmeter.test_elements;

import lombok.experimental.Builder;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testelement.TestElement;

@Builder
public class HTTPArgumentElement extends JMeterStepImpl<HTTPArgumentElement> {
    private String name;
    private String value;

    public TestElement getTestElement() {
        HTTPArgument httpArgument = new HTTPArgument();
        httpArgument.setName(name);
        httpArgument.setValue(value);
        httpArgument.setMetaData("=");
        httpArgument.setUseEquals(true);
        httpArgument.setAlwaysEncoded(true);
        return httpArgument;
    }

}
