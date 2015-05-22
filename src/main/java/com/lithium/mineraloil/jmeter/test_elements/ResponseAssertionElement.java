package com.lithium.mineraloil.jmeter.test_elements;

import lombok.experimental.Builder;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.testelement.TestElement;

@Builder
public class ResponseAssertionElement extends JMeterStepImpl<ResponseAssertionElement>{
    private ResponseSampleType responseSampleType;
    private ResponseField responseField;
    private ResponsePatternType responsePatternType;
    private boolean ignoreStatus;
    private boolean not;
    private String testString;
    private String name;

    public TestElement getTestElement() {
        ResponseAssertion assertion = new ResponseAssertion();
        assertion.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.assertions.gui.AssertionGui");
        assertion.setProperty(TestElement.TEST_CLASS, "org.apache.jmeter.assertions.ResponseAssertion");
        assertion.setProperty(TestElement.ENABLED, true);
        assertion.setName(name);
        switch (responseField) {
            case TEXT:
                assertion.setTestFieldResponseData();
                break;
            case DOCUMENT:
                assertion.setTestFieldResponseDataAsDocument();
                break;
            case URL:
                assertion.setTestFieldURL();
                break;
            case RESPONSE_CODE:
                assertion.setTestFieldResponseCode();
                break;
            case RESPONSE_MESSAGE:
                assertion.setTestFieldResponseMessage();
                break;
            case RESPONSE_HEADERS:
                assertion.setTestFieldResponseHeaders();
                break;
        }
        if (ignoreStatus) assertion.setAssumeSuccess(!ignoreStatus);
        switch (responsePatternType) {
            case CONTAINS:
                assertion.setToContainsType();
                break;
            case MATCHES:
                assertion.setToMatchType();
                break;
            case EQUALS:
                assertion.setToEqualsType();
                break;
            case SUBSTRING:
                assertion.setToSubstringType();
                break;
        }
        if (not) assertion.setToNotType();
        assertion.addTestString(testString);
        return assertion;
    }
}
