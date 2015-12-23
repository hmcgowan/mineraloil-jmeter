package com.lithium.mineraloil.jmeter.test_elements;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.experimental.Builder;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HTTPArgumentsPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler2;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testelement.TestElement;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class HTTPSamplerElement extends JMeterStepImpl<HTTPSamplerElement> {
    private String domain;
    private int port;
    private String path;
    private String method;
    private List<HTTPArgument> arguments;
    private Integer connectTimeout;
    private Integer responseTimeout;
    private Boolean followRedirects;
    private String protocol;
    private String contentEncoding;
    private Boolean autoRedirects;
    private Boolean useKeepAlive;
    private Boolean doMultiPartPost;
    private Boolean monitor;
    private String embeddedUrlRE;
    private String implementation;
    private HeaderManager headerManager;

    public TestElement getTestElement() {
        Preconditions.checkNotNull(domain);
        Preconditions.checkNotNull(port);
        Preconditions.checkNotNull(path);

        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();

        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName().toString());
        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName().toString());
        httpSampler.setName(getPath().replaceAll("\\?.*", "") + " " + method + " " + String.valueOf(isReportable).toUpperCase()); // user the path without arguments
        httpSampler.setEnabled(true);
        httpSampler.setDomain(domain);
        httpSampler.setPort(port);
        httpSampler.setPath(path);
        httpSampler.setMethod(getOptionalValue(method, "GET"));
        httpSampler.setHeaderManager(headerManager);

        // optional parameters
        httpSampler.setImplementation(getOptionalValue(implementation, "HttpClient4"));
        httpSampler.setConnectTimeout(String.valueOf(getOptionalValue(connectTimeout, 120000)));
        httpSampler.setResponseTimeout(String.valueOf(getOptionalValue(responseTimeout, 120000)));
        httpSampler.setProtocol(getOptionalValue(protocol, "HTTPS"));
        httpSampler.setContentEncoding(getOptionalValue(contentEncoding, "UTF-8"));
        if (getOptionalValue(followRedirects, false)) {
            httpSampler.setFollowRedirects(getOptionalValue(followRedirects, false));
        } else {
            httpSampler.setAutoRedirects(getOptionalValue(autoRedirects, true));
        }
        httpSampler.setUseKeepAlive(getOptionalValue(useKeepAlive, true));
        httpSampler.setDoMultipartPost(getOptionalValue(doMultiPartPost, false));
        httpSampler.setMonitor(getOptionalValue(monitor, false));
        httpSampler.setEmbeddedUrlRE(getOptionalValue(embeddedUrlRE,""));
        httpSampler.setArguments(getArgumentsElement(arguments));
        return httpSampler;
    }

    private Arguments getArgumentsElement(List<HTTPArgument> httpArguments) {
        Arguments arguments = new Arguments();
        arguments.setProperty(TestElement.GUI_CLASS, HTTPArgumentsPanel.class.getName());
        arguments.setProperty(TestElement.TEST_CLASS, Arguments.class.getName());
        arguments.setProperty(TestElement.ENABLED, true);
        if (httpArguments != null) {
            for (HTTPArgument httpArgument : httpArguments) {
                arguments.addArgument(httpArgument);
            }
        }
        return arguments;
    }

    public HTTPSamplerElement addArgument(String name, Object value) {
        return addArgument(name, String.valueOf(value));
    }

    public HTTPSamplerElement addArgument(String name, String value) {
        if (arguments == null) arguments = new ArrayList<>();
        HTTPArgument argument = (HTTPArgument) HTTPArgumentElement.builder()
                                                                  .name(name)
                                                                  .value(value)
                                                                  .build()
                                                                  .getTestElement();
        arguments.add(argument);
        return this;
    }
}
