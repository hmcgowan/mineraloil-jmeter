package com.lithium.mineraloil.jmeter.test_elements;

import lombok.Getter;
import lombok.experimental.Builder;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.timers.ConstantThroughputTimer;

@Builder
@Getter
public class ConstantThroughputTimerElement extends JMeterStepImpl<ConstantThroughputTimerElement> {
    private int throughput;
    private ConstantThroughputTimerCalcMode calcMode;

    public TestElement getTestElement() {
        ConstantThroughputTimer timer = new ConstantThroughputTimer();
        timer.setName("Constant Throughput Timer");
        timer.setComment("Used to throttle the amount of activity in any given minute to simulate real user loads");
        timer.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.testbeans.gui.TestBeanGUI");
        timer.setProperty(TestElement.TEST_CLASS, "org.apache.jmeter.timers.ConstantThroughputTimer");
        timer.setProperty(TestElement.ENABLED, true);
        // not sure why using the build in methods fail, maybe version issue in JMeter 2.11
        timer.setProperty("throughput", throughput);
        timer.setProperty("calcMode", calcMode.getIndex());
        return timer;
    }

}
