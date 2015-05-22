package com.lithium.mineraloil.jmeter.test_elements;

import lombok.experimental.Builder;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.testelement.TestElement;

@Builder
public class HeaderElement extends JMeterStepImpl<HeaderElement>{
    private String name;
    private String value;

    public TestElement getTestElement() {
        Header header = new Header();
        header.setName(name);
        header.setValue(value);
        return header;
    }

}
