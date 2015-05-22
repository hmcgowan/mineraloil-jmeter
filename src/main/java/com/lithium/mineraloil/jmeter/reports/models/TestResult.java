package com.lithium.mineraloil.jmeter.reports.models;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name="testResults")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestResult {
    @XmlAttribute
    private String version;

    @XmlElement(name = "httpSample")
    private List<HTTPSample> httpSamples;
}
