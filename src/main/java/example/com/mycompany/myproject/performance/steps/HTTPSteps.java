package example.com.mycompany.myproject.performance.steps;

import com.lithium.mineraloil.jmeter.test_elements.HTTPSamplerElement;

public class HTTPSteps {
    private static String domain = "0.0.0.0";
    private static int port = 8000;
    private static String protocol = "http";

    public static HTTPSamplerElement login() {
        return HTTPSamplerElement.builder()
                                 .domain(domain)
                                 .port(port)
                                 .protocol(protocol)
                                 .path("/EXAMPLE.html")
                                 .useKeepAlive(true)
                                 .followRedirects(true)
                                 .doMultiPartPost(true)
                                 .method("GET")
                                 .build()
                                 .addArgument("user", "jsmith")
                                 .addArgument("password", "Demo1234")
                                 .addStep(AssertionSteps.isLoggedIn());
    }

}
