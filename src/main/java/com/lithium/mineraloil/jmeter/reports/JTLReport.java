package com.lithium.mineraloil.jmeter.reports;

import com.lithium.mineraloil.jmeter.reports.models.HTTPSample;
import com.lithium.mineraloil.jmeter.reports.models.TestResult;
import lithium.datainv.classifier.PageNameClassifier;
import lithium.util.Config;
import lombok.Getter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collectors;

public class JTLReport {
    @Getter
    private TestResult testResult;
    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;
    private Marshaller marshaller;

    private String isReportable = "TRUE";

    public JTLReport(String filename) {
        try {
            testResult = parse(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createReportableResults(String filename) {
        TestResult reportableSubset = new TestResult();
        reportableSubset.setVersion(testResult.getVersion());
        reportableSubset.setHttpSamples(
                testResult.getHttpSamples().stream()
                          .filter(sample -> sample.getLb().endsWith(isReportable))
                          .collect(Collectors.toList())
                                       );
        try {
            marshaller.marshal(reportableSubset, new File(filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    public void createReportableResultsWithPageClassification(String filename) {
        TestResult reportableSubset = new TestResult();
        reportableSubset.setVersion(testResult.getVersion());

        reportableSubset.setHttpSamples(
                setSampleLabel(testResult).stream()
                        //.filter(sample -> sample.getLb().endsWith(isReportable))
                        .collect(Collectors.toList())
        );
        try {
            marshaller.marshal(reportableSubset, new File(filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private TestResult parse(String filename) throws Exception {
        if (jaxbContext == null) {
            jaxbContext = JAXBContext.newInstance(TestResult.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        }
        return (TestResult) unmarshaller.unmarshal(new FileInputStream(filename));
    }
    private List<HTTPSample> setSampleLabel(TestResult testResult){
        Config config = new lithium.util.Config();
        config.put("tapestry.context.name", "t5");
        PageNameClassifier pageNameClassifier = new PageNameClassifier(config);

        for (int i=0;i<testResult.getHttpSamples().size();i++)
        {
            String pageName = pageNameClassifier.classify(testResult.getHttpSamples().get(i).getLb().split(" ")[0]);
            testResult.getHttpSamples().get(i).setLb(pageName);
        }
        return testResult.getHttpSamples();
    }
}
