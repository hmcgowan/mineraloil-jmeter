package com.lithium.mineraloil.jmeter.test_elements;

import lombok.Getter;

public enum ConstantThroughputTimerCalcMode {
    THIS_THREAD_ONLY(0),
    ALL_ACTIVE_THREADS(1),
    ALL_ACTIVE_THREADS_CURRENT_THREAD_GROUP(2),
    ALL_ACTIVE_THREADS_SHARED(3),
    ALL_ACTIVE_THREADS_CURRENT_THREAD_GROUP_SHARED(4);

    @Getter
    int index;

    ConstantThroughputTimerCalcMode(int index) {
        this.index = index;
    }
}
