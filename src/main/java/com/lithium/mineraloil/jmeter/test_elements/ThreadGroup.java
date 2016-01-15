package com.lithium.mineraloil.jmeter.test_elements;

public class ThreadGroup {

    /*
    * Returns a ThreadGroupElement with default of 1 loop count and 1 thread count
    *
    * @param    name    The name of the thread group
    * @return           A ThreadGroupElement that steps can be added
    */
    public static ThreadGroupElement create(String name) {
        return ThreadGroupElement.builder()
                                 .name(name)
                                 .loopCount(1)
                                 .threadCount(1)
                                 .build();
    }

    /*
    * Returns a ThreadGroupElement with specified loop count and thread count
    *
    * @param    name            The name of the thread group
    * @param    threadcount     Number of threads run in the thread group
    * @param    loopcount       Number of loops of the thread group to run
    * @return                   A ThreadGroupElement that steps can be added
    */
    public static ThreadGroupElement create(String name, int threadCount, int loopCount) {
        return ThreadGroupElement.builder()
                                 .name(name)
                                 .threadCount(threadCount)
                                 .loopCount(loopCount)
                                 .build();
    }
}
