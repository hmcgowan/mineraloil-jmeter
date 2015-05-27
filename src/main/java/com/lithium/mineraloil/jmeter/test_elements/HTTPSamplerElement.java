package com.lithium.mineraloil.jmeter.test_elements;

import lombok.Getter;
import lombok.experimental.Builder;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testelement.TestElement;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class HTTPSamplerElement extends JMeterStepImpl<HTTPSamplerElement> {
    private String domain;
    private int port;
    private String path;
    private String method;
    private Optional<List<HTTPArgument>> arguments;
    private Optional<Integer> connectTimeout;
    private Optional<Integer> responseTimeout;
    private Optional<Boolean> followRedirects;
    private Optional<String> protocol;
    private Optional<String> contentEncoding;
    private Optional<Boolean> autoRedirects;
    private Optional<Boolean> useKeepAlive;
    private Optional<Boolean> doMultiPartPost;
    private Optional<Boolean> monitor;
    private Optional<String> embeddedUrlRE;
    private Optional<String> implementation;

    public TestElement getTestElement() {
        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setName(getPath().replaceAll("\\?.*", "") + " " + method + " " + String.valueOf(isReportable).toUpperCase()); // user the path without arguments
        httpSampler.setEnabled(true);
        httpSampler.setImplementation(implementation.orElse("HttpClient4"));
        httpSampler.setDomain(domain);
        httpSampler.setPort(port);
        httpSampler.setPath(path);
        httpSampler.setMethod(method);

        // optional parameters
        httpSampler.setConnectTimeout(String.valueOf(connectTimeout.orElse(120000)));
        httpSampler.setResponseTimeout(String.valueOf(responseTimeout.orElse(120000)));
        httpSampler.setFollowRedirects(followRedirects.orElse(false));
        httpSampler.setProtocol(protocol.orElse("HTTPS"));
        httpSampler.setContentEncoding(contentEncoding.orElse("UTF-8"));
        httpSampler.setAutoRedirects(autoRedirects.orElse(true));
        httpSampler.setUseKeepAlive(useKeepAlive.orElse(true));
        httpSampler.setDoMultipartPost(doMultiPartPost.orElse(false));
        httpSampler.setMonitor(monitor.orElse(false));
        httpSampler.setEmbeddedUrlRE(embeddedUrlRE.orElse(""));
        if (arguments.isPresent()) httpSampler.setArguments(getArgumentsElement(arguments));
        return httpSampler;
    }

    private Arguments getArgumentsElement(Optional<List<HTTPArgument>> httpArguments) {
        Arguments arguments = new Arguments();
        arguments.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.protocol.http.gui.HTTPArgumentsPanel");
        arguments.setProperty(TestElement.TEST_CLASS, "org.apache.jmeter.config.Arguments");
        arguments.setProperty(TestElement.ENABLED, true);
        if (httpArguments != null) {
            for (HTTPArgument httpArgument : httpArguments.get()) {
                arguments.addArgument(httpArgument);
            }
        }
        return arguments;
    }

    public HTTPSamplerElement addArgument(String name, Object value) {
        return addArgument(name, String.valueOf(value));
    }

    public HTTPSamplerElement addArgument(String name, String value) {
        HTTPArgument argument = (HTTPArgument) HTTPArgumentElement.builder()
                                                   .name(name)
                                                   .value(value)
                                                   .build()
                                                   .getTestElement();
        arguments.get().add(argument);
        return this;
    }
}
