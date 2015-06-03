package com.lithium.mineraloil.jmeter.reports;

import com.lithium.mineraloil.jmeter.reports.models.HTTPSample;
import com.lithium.mineraloil.jmeter.reports.models.TestResult;
import lombok.Getter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;

public class SummaryReport {
    @Getter
    private TestResult testResult;
    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;
    private Marshaller marshaller;

    public SummaryReport(String filename) {
        try {
            testResult = parse(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showOutputInLog();
    }

    public boolean isSuccessful() {
        return !isErrorSeen() && !isFailureSeen();
    }

    public boolean isFailureSeen() {
        return testResult.getHttpSamples().stream().anyMatch(row -> row.getAssertionResult() != null && row.getAssertionResult().getFailure() == true);
    }

    public boolean isErrorSeen() {
        return testResult.getHttpSamples().stream().anyMatch(row -> row.getAssertionResult() != null && row.getAssertionResult().getError() == true);
    }

    public void showOutputInLog() {
        for (HTTPSample httpSample : testResult.getHttpSamples()) {
            try {
                marshaller.marshal(httpSample, System.out );
                System.out.println();
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    public String getErrorRate() {
        return String.format("%.2f%%", 100 * (double) testResult.getHttpSamples().stream().mapToInt(HTTPSample::getEc).sum() / (double) testResult.getHttpSamples().stream().mapToInt(HTTPSample::getSc).sum());
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

}
