package com.lithium.mineraloil.jmeter.test_elements;

import drivers.selenium_drivers.DriverManager;
import lombok.experimental.Builder;
import lsw.config.ConfigurationLoader;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;

@Builder
public class ThreadGroupElement extends JMeterStepImpl<ThreadGroupElement> {

    private final ConfigurationLoader config = DriverManager.getConfiguration();

    private String name;
    private int userCount;
    private int rampUp;
    private int loopCount;


    public TestElement getTestElement() {
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        threadGroup.setProperty(TestElement.ENABLED, true);
        threadGroup.setProperty(TestElement.NAME, name);
        threadGroup.setScheduler(false);
        threadGroup.setDelay(0);
        threadGroup.setDuration(0);
        if (userCount == 0) {
            userCount = config.getJMeterUserCount();
        }
        threadGroup.setNumThreads(userCount);

        if (rampUp == 0) {
            rampUp = config.getJMeterRampUp();
        }
        threadGroup.setRampUp(rampUp);

        if (loopCount == 0) {
            loopCount = config.getJMeterLoopCount();
        }
        threadGroup.setSamplerController(getLoopController(loopCount));

        threadGroup.setScheduler(false);
        threadGroup.setDelay(0);
        threadGroup.setDuration(0);
        return threadGroup;
    }

    private LoopController getLoopController(int loopCount) {
        LoopController loopController = new LoopController();
        loopController.setLoops(loopCount);
        loopController.setContinueForever(false);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.setProperty(TestElement.ENABLED, true);
        loopController.initialize();
        return loopController;
    }
}
