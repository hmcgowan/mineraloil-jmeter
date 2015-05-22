package com.lithium.mineraloil.jmeter.test_elements;

public enum CSVFiles {
    USERS("user.csv"),
    CASES("cases.csv");
    private final String name;
    CSVFiles(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
