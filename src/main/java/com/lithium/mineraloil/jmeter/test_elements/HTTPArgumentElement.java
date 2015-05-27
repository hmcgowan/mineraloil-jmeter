package com.lithium.mineraloil.jmeter.test_elements;

import lombok.experimental.Builder;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testelement.TestElement;

import java.util.Optional;

@Builder
public class HTTPArgumentElement extends JMeterStepImpl<HTTPArgumentElement> {
    private String name;
    private String value;
    private Optional<String> metaData;
    private Optional<Boolean> useEquals;
    private Optional<Boolean> setAlwaysEncoded;

    public TestElement getTestElement() {
        HTTPArgument httpArgument = new HTTPArgument();
        httpArgument.setName(name);
        httpArgument.setValue(value);
        httpArgument.setMetaData(metaData.orElse("="));
        httpArgument.setUseEquals(useEquals.orElse(true));
        httpArgument.setAlwaysEncoded(setAlwaysEncoded.orElse(true));
        return httpArgument;
    }

}
