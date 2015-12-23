package com.lithium.mineraloil.jmeter.test_elements;

import com.google.common.base.Preconditions;
import lombok.experimental.Builder;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;

@Builder
public class ThreadGroupElement extends JMeterStepImpl<ThreadGroupElement> {
    private String name;
    private Integer threadCount;
    private Integer rampUp;
    private Integer delay;
    private Integer duration;
    private Boolean setScheduler;
    private Integer loopCount;
    private Boolean continueForever;
    private Boolean isFirst;

    public TestElement getTestElement() {
        Preconditions.checkNotNull(name);

        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        threadGroup.setName(name);
        threadGroup.setEnabled(true);
        threadGroup.setScheduler(false);
        threadGroup.setNumThreads(getOptionalValue(threadCount, 1));
        threadGroup.setRampUp(getOptionalValue(rampUp, 0));
        threadGroup.setDelay(getOptionalValue(delay, 0));
        threadGroup.setDuration(getOptionalValue(duration, 0));
        threadGroup.setScheduler(getOptionalValue(setScheduler, false));
        LoopElement loopController = LoopElement.builder()
                                                .name(name + "_loopController")
                                                .loopCount(loopCount)
                                                .continueForever(continueForever)
                                                .isFirst(isFirst)
                                                .build();

        threadGroup.setSamplerController(loopController.getLoopController());
        ((LoopController)threadGroup.getSamplerController()).setContinueForever(continueForever);
        return threadGroup;
    }
}
