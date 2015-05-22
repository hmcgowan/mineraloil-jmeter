package com.lithium.mineraloil.jmeter.test_elements;

import com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor;
import lombok.experimental.Builder;
import org.apache.jmeter.testelement.TestElement;

@Builder
public class JSONPathExtractorElement extends JMeterStepImpl<JSONPathExtractorElement> {

    private String name;
    private String toVariable;
    private String jsonPath;
    private String defaultValue;
    private String fromVariable;
    private String subject;

    public TestElement getTestElement() {
        JSONPathExtractor jsonPathExtractor = new JSONPathExtractor();
        jsonPathExtractor.setProperty(TestElement.GUI_CLASS, "com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.gui.JSONPathExtractorGui");
        jsonPathExtractor.setProperty(TestElement.TEST_CLASS, "com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor");
        jsonPathExtractor.setProperty(TestElement.NAME, name);
        jsonPathExtractor.setProperty(TestElement.ENABLED, true);
        jsonPathExtractor.setVar(toVariable);
        jsonPathExtractor.setJsonPath(jsonPath);
        jsonPathExtractor.setDefaultValue(defaultValue);
        jsonPathExtractor.setSrcVariableName(fromVariable);
        jsonPathExtractor.setSubject(subject);
        return jsonPathExtractor;
    }

}
