package com.lithium.mineraloil.jmeter.test_elements;

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
        CSVDataSet csvDataSet = new CSVDataSet();
        csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        csvDataSet.setProperty(TestElement.TEST_CLASS, CSVDataSet.class.getName());
        csvDataSet.setName(name);
        csvDataSet.setEnabled(true);
        csvDataSet.setFilename(getOptionalValue(fileName, String.format("%s%s", outputFilePath, fileName.toString())));
        csvDataSet.setFileEncoding(fileEncoding);
        csvDataSet.setVariableNames(variableNames);
        csvDataSet.setDelimiter(delimiter);
        csvDataSet.setQuotedData(quotedData);
        csvDataSet.setRecycle(recycle);
        csvDataSet.setStopThread(stopThread);
        csvDataSet.setShareMode(shareMode);
        return csvDataSet;
    }
}
