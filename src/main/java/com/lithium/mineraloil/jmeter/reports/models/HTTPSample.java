package com.lithium.mineraloil.jmeter.reports.models;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name="httpSample")
@XmlAccessorType(XmlAccessType.FIELD)
public class HTTPSample {
    @XmlAttribute
    private int t;

    @XmlAttribute
    private int lt;

    @XmlAttribute
    private long ts;

    @XmlAttribute
    private boolean s;

    @XmlAttribute
    private String lb;

    @XmlAttribute
    private int rc;

    @XmlAttribute
    private String tn;

    @XmlAttribute
    private int by;

    @XmlAttribute
    private int sc;

    @XmlAttribute
    private int ec;

    @XmlAttribute
    private int ng;

    @XmlAttribute
    private int na;

    @XmlAttribute
    private String hn;

    @XmlElement
    private AssertionResult assertionResult;

    @XmlElement(name="responseHeader")
    private SimpleTextElement responseHeader;

    @XmlElement(name="requestHeader")
    private SimpleTextElement requestHeader;

    @XmlElement(name="method")
    private SimpleTextElement method;

    @XmlElement(name="queryString")
    private SimpleTextElement queryString;

    @XmlElement(name="redirectLocation")
    private SimpleTextElement redirectLocation;

    @XmlElement(name="cookies")
    private SimpleTextElement cookies;
}
