package com.lithium.mineraloil.jmeter.test_elements;

import com.google.common.base.Preconditions;
import lombok.experimental.Builder;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.testelement.TestElement;

@Builder
public class HeaderElement extends JMeterStepImpl<HeaderElement>{
    private String name;
    private String value;

    public TestElement getTestElement() {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(value);

        Header header = new Header();
        header.setName(name);
        header.setValue(value);
        return header;
    }

}
