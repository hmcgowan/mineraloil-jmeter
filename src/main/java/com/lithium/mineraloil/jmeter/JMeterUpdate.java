package com.lithium.mineraloil.jmeter;

import lombok.Data;

@Data
public class JMeterUpdate {
    private JMeterStatus state;
    private String testPlanName;
}
