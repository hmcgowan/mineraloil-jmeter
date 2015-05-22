package com.lithium.mineraloil.jmeter.test_elements;

import lombok.experimental.Builder;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.testelement.TestElement;

@Builder
public class LoopElement extends JMeterStepImpl<LoopElement> {

    private String name;
    private boolean continueForever;
    private int loopCount;

    public TestElement getTestElement() {
        LoopController loopController = new LoopController();
        loopController.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.control.gui.LoopControlPanel");
        loopController.setProperty(TestElement.TEST_CLASS, "org.apache.jmeter.control.LoopController");
        loopController.setProperty(TestElement.NAME, name);
        loopController.setProperty(TestElement.ENABLED, true);
        loopController.setProperty("LoopController.continue_forever", continueForever);
        loopController.setProperty("LoopController.loops", loopCount);

        return loopController;
    }
}
