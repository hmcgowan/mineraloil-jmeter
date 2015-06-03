package com.lithium.mineraloil.jmeter.test_elements;

import lombok.Getter;
import lombok.experimental.Builder;
import org.apache.jmeter.extractor.RegexExtractor;
import org.apache.jmeter.extractor.gui.RegexExtractorGui;
import org.apache.jmeter.testelement.TestElement;

@Builder
@Getter
public class RegularExpressionExtractorElement extends JMeterStepImpl<RegularExpressionExtractorElement> {
    private String name;
    private String referenceName;
    private String regex;
    private String template;
    private String matchNumber;
    private String defaultValue;

    public TestElement getTestElement() {
        RegexExtractor regexExtractor = new RegexExtractor();
        regexExtractor.setProperty(TestElement.GUI_CLASS, RegexExtractorGui.class.getName().toString());
        regexExtractor.setProperty(TestElement.TEST_CLASS, RegexExtractor.class.getName().toString());
        regexExtractor.setName(name);
        regexExtractor.setRefName(referenceName);
        regexExtractor.setRegex(regex);
        regexExtractor.setTemplate(template);
        regexExtractor.setMatchNumber(matchNumber);
        regexExtractor.setDefaultValue(defaultValue);
        return regexExtractor;
    }
}
