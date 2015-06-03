package com.lithium.mineraloil.jmeter.test_elements;

import com.google.common.base.Preconditions;
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
        Preconditions.checkNotNull(name);
        RegexExtractor regexExtractor = new RegexExtractor();
        regexExtractor.setProperty(TestElement.GUI_CLASS, RegexExtractorGui.class.getName().toString());
        regexExtractor.setProperty(TestElement.TEST_CLASS, RegexExtractor.class.getName().toString());
        regexExtractor.setProperty(TestElement.NAME, name);
        regexExtractor.setProperty("RegexExtractor.refname", referenceName);
        regexExtractor.setProperty("RegexExtractor.regex", regex);
        regexExtractor.setProperty("RegexExtractor.template", template);
        regexExtractor.setProperty("RegexExtractor.match_number", matchNumber);
        regexExtractor.setProperty("RegexExtractor.default", getOptionalValue(defaultValue, ""));
        return regexExtractor;
    }
}
