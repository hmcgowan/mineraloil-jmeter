package com.lithium.mineraloil.jmeter.test_elements;

import lombok.experimental.Builder;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testelement.TestElement;

@Builder
public class HTTPArgumentElement extends JMeterStepImpl<HTTPArgumentElement> {
    private String name;
    private String value;
    private String metaData;
    private Boolean useEquals;
    private Boolean setAlwaysEncoded;

    public TestElement getTestElement() {
        HTTPArgument httpArgument = new HTTPArgument();
        httpArgument.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName().toString());
        httpArgument.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName().toString());
        httpArgument.setName(name);
        httpArgument.setValue(value);
        httpArgument.setMetaData(getOptionalValue(metaData, "="));
        httpArgument.setUseEquals(getOptionalValue(useEquals, true));
        httpArgument.setAlwaysEncoded(getOptionalValue(setAlwaysEncoded, true));
        return httpArgument;
    }

}
