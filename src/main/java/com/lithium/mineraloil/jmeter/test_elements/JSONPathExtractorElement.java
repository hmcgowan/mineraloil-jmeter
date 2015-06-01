package com.lithium.mineraloil.jmeter.test_elements;

import com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor;
import com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.gui.JSONPathExtractorGui;
import com.google.common.base.Preconditions;
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
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(jsonPath);
        Preconditions.checkNotNull(toVariable);
        Preconditions.checkNotNull(defaultValue);
        Preconditions.checkNotNull(fromVariable);

        JSONPathExtractor jsonPathExtractor = new JSONPathExtractor();
        jsonPathExtractor.setProperty(TestElement.GUI_CLASS, JSONPathExtractorGui.class.getName());
        jsonPathExtractor.setProperty(TestElement.TEST_CLASS, JSONPathExtractor.class.getName());
        jsonPathExtractor.setName(name);
        jsonPathExtractor.setEnabled(true);
        jsonPathExtractor.setVar(toVariable);
        jsonPathExtractor.setJsonPath(jsonPath);
        jsonPathExtractor.setDefaultValue(defaultValue);
        jsonPathExtractor.setSrcVariableName(fromVariable);
        jsonPathExtractor.setSubject(subject);
        return jsonPathExtractor;
    }

}
