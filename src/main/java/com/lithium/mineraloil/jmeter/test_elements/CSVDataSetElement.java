package com.lithium.mineraloil.jmeter.test_elements;

import com.google.common.base.Preconditions;
import lombok.experimental.Builder;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;

@Builder
public class CSVDataSetElement extends JMeterStepImpl<CSVDataSetElement> {
    private String name;
    private String fileEncoding;
    private String variableNames;
    private String delimiter;
    private Boolean quotedData;
    private Boolean recycle;
    private Boolean stopThread;
    private String shareMode;
    private String fileName;

    public TestElement getTestElement() {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(fileName);

        CSVDataSet csvDataSet = new CSVDataSet();
        csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        csvDataSet.setProperty(TestElement.TEST_CLASS, CSVDataSet.class.getName());
        csvDataSet.setName(name);
        csvDataSet.setEnabled(true);

        // calling the setters doesn't work in jmeter 2.11
        csvDataSet.setProperty("filename", getOptionalValue(fileName, String.format("%s%s", outputFilePath, fileName.toString())));
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
