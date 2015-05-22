package com.lithium.mineraloil.jmeter.test_elements;

import lombok.Getter;
import lombok.Setter;
import org.apache.jmeter.testelement.TestElement;

import java.util.ArrayList;
import java.util.List;

public abstract class JMeterStepImpl<T extends JMeterStep> implements JMeterStep {
    @Getter @Setter
    public String outputFilePath;

    @Getter @Setter
    public boolean isReportable;

    @Getter
    private List<JMeterStep> steps = new ArrayList<>();

    public T addStep(JMeterStep step) {
        steps.add(step);
        return (T) this;
    }

    public T addReportableStep(JMeterStep step) {
        if (steps == null) steps = new ArrayList<>();
        step.setReportable(true);
        steps.add(step);
        return (T) this;
    }

    @Override
    public abstract <T extends TestElement> T getTestElement();

}
