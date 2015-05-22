package com.lithium.mineraloil.jmeter.reports.models;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@Setter
public class SimpleTextElement {
    @XmlAttribute(name="class")
    private String className;

    @XmlValue
    protected String value;
}
