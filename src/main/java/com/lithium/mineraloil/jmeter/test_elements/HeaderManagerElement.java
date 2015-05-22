package com.lithium.mineraloil.jmeter.test_elements;

import lombok.Getter;
import lombok.experimental.Builder;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.testelement.TestElement;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class HeaderManagerElement extends JMeterStepImpl<HeaderManagerElement>{

    private String name;
    private List<Header> headers;

    public TestElement getTestElement() {
        HeaderManager headerManager = new HeaderManager();
        headerManager.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.protocol.http.gui.HeaderPanel" );
        headerManager.setProperty(TestElement.TEST_CLASS, "org.apache.jmeter.protocol.http.control.HeaderManager");
        headerManager.setProperty(TestElement.NAME, "HTTP Header Manager");
        headerManager.setProperty(TestElement.ENABLED, true);
        for (Header header : headers) {
            headerManager.add(header);
        }
        return headerManager;
    }


    public HeaderManagerElement addHeader(String name, String value) {
        if (headers== null) headers = new ArrayList<>();
        Header header = (Header) HeaderElement.builder()
                                              .name(name)
                                              .value(value)
                                              .build()
                                              .getTestElement();
        headers.add(header);
        return this;
    }



}
