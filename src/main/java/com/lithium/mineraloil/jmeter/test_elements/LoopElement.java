package com.lithium.mineraloil.jmeter.test_elements;

import lombok.experimental.Builder;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.testelement.TestElement;

import java.util.Optional;

@Builder
public class LoopElement extends JMeterStepImpl<LoopElement> {

    private String name;
    private Optional<Integer> loopCount;
    private Optional<Boolean> continueForever;
    private Optional<Boolean> isFirst;

    // for cases where it's a test element
    public TestElement getTestElement() {
        return getLoopController();
    }

    // for cases where it's pulled in to a ThreadGroupElement
    public LoopController getLoopController() {
        LoopController loopController = new LoopController();
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.setEnabled(true);
        loopController.setLoops(loopCount.orElse(1));
        loopController.setContinueForever(continueForever.orElse(false));
        loopController.setFirst(isFirst.orElse(true));
        return loopController;
    }
}
