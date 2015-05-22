package com.lithium.mineraloil.jmeter.test_elements;

import lombok.experimental.Builder;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.testelement.TestElement;

@Builder
public class CSVDataSetElement extends JMeterStepImpl<CSVDataSetElement> {
    private String name;
    private String fileName;
    private String fileEncoding;
    private String variableNames;
    private String delimiter;
    private Boolean quotedData;
    private Boolean recycle;
    private Boolean stopThread;
    private String shareMode;

    public TestElement getTestElement() {
        CSVDataSet csvDataSet = new CSVDataSet();
        csvDataSet.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.testbeans.gui.TestBeanGUI");
        csvDataSet.setProperty(TestElement.TEST_CLASS, "org.apache.jmeter.config.CSVDataSet");
        csvDataSet.setProperty(TestElement.NAME, name);
        csvDataSet.setProperty(TestElement.ENABLED, true);
        csvDataSet.setProperty("filename", String.format("%s%s", outputFilePath, fileName.toString()));
        csvDataSet.setProperty("fileEncoding", fileEncoding);
        csvDataSet.setProperty("variableNames", variableNames);
        csvDataSet.setProperty("delimiter",delimiter);
        csvDataSet.setProperty("quotedData", quotedData);
        csvDataSet.setProperty("recycle", recycle);
        csvDataSet.setProperty("stopThread", stopThread);
        csvDataSet.setProperty("shareMode", shareMode);
        return csvDataSet;
    }
}
