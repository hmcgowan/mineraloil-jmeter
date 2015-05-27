package com.lithium.mineraloil.jmeter.test_elements;

import lombok.experimental.Builder;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;

import java.util.Optional;

@Builder
public class ThreadGroupElement extends JMeterStepImpl<ThreadGroupElement> {
    private String name;
    private Optional<Integer> threadCount;
    private Optional<Integer> rampUp;
    private Optional<Integer> delay;
    private Optional<Integer> duration;
    private Optional<Boolean> setScheduler;
    private Optional<Integer> loopCount;
    private Optional<Boolean> continueForever;
    private Optional<Boolean> isFirst;

    public TestElement getTestElement() {
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        threadGroup.setName(name);
        threadGroup.setEnabled(true);
        threadGroup.setScheduler(false);
        threadGroup.setNumThreads(threadCount.orElse(1));
        threadGroup.setRampUp(rampUp.orElse(0));
        threadGroup.setDelay(delay.orElse(0));
        threadGroup.setDuration(duration.orElse(0));
        threadGroup.setScheduler(setScheduler.orElse(false));
        LoopElement loopController = LoopElement.builder()
                                                .loopCount(loopCount)
                                                .continueForever(continueForever)
                                                .isFirst(isFirst)
                                                .build();
        threadGroup.setSamplerController(loopController.getLoopController());
        return threadGroup;
    }
}
