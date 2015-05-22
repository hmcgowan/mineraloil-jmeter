package com.lithium.mineraloil.jmeter.reports.models;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class AssertionResult {
    @XmlElement
    private String name;

    @XmlElement
    private boolean failure;

    @XmlElement
    private boolean error;

    // these getters need to be here because not for JAXB
    // but because we're trying to call them in our code
    // and the xml annotations don't

    public String getName() {
        return name;
    }

    public boolean getFailure() {
        return failure;
    }

    public boolean getError() {
        return error;
    }

}
