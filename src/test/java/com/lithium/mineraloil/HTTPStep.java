package com.lithium.mineraloil;

import com.lithium.mineraloil.jmeter.test_elements.HTTPSamplerElement;
import org.junit.Test;

public class HTTPStep {

    @Test
    public void httpRequest() {
        HTTPSamplerElement.builder()
                          .path("/account/login")
                          .method("GET")
                          .build();
    }
}
