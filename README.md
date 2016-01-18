# mineraloil-jmeter
Run JMeter without the GUI

JMeter is a great tool but when you build out a full performance suite you end up having to maintain a lot of XML which is a burden and lends itself to a lot of repetition. This library allows you to call JMeter directly through Java and maintain your test code in Java, while still creating JMX files so you can debug issues in the native JMeter application. 

One of the benefits to this approach is being able to encapsulate and share common methods easily. This allows the scripts to be maintainable and easy to read. For example, this is a simple login test:

```java
public class UserLoginTest {

    @Test
    public void loginTest() {
        JMeterRunner jmeter = new JMeterRunner("Login");
        jmeter.addStep(ThreadGroupSteps.createThreadGroup("Login Test")
                                       .addReportableStep(HTTPSteps.login()));
        jmeter.run();
        Assert.assertTrue("Test run failed. Error rate: " + jMeter.getSummaryResults().getErrorRate(),        
                          jMeter.getSummaryResults().isSuccessful());
    }
}

public class HTTPSteps {
    private static String domain = "someDomain";
    private static int port = 80;
    private static String protocol = "http";

    public static HTTPSamplerElement login() {
        return HTTPSamplerElement.builder()
                                 .domain(domain)
                                 .port(port)
                                 .protocol(protocol)
                                 .path("/path/to/login/form")
                                 .useKeepAlive(true)
                                 .followRedirects(true)
                                 .doMultiPartPost(true)
                                 .method("POST")
                                 .build()
                                 .addArgument("login", "someUsername")
                                 .addArgument("password", "somePassword")
                                 .addStep(AssertionSteps.isLoggedIn());
    }
}

public class AssertionSteps {
    public static ResponseAssertionElement isLoggedIn() {
        return ResponseAssertionElement.builder()
                                       .name("Assert user is logged in")
                                       .responseSampleType(ResponseSampleType.MAIN_SAMPLE)
                                       .responseField(ResponseField.TEXT)
                                       .responsePatternType(ResponsePatternType.CONTAINS)
                                       .not(false)
                                       .testString("<a>Sign Out</a>")
                                       .build();
    }
}
```

So in this case we've created an HTTPSampler Element that is specific to (and reusable for) logging into the application.

The HTTPSteps.login() method will of course vary application to application as will the assertion that the user is logged in. Now that we have this set up, we can reuse the login method for other tests. If you look in the target/jmeter directory you should see the raw output files in addition to a .jmx file that you can then open and run in JMeter if you like. 

In the same manner you can imagine building out a suite of reusable steps that could add items to a cart, view cart contents, complete a purchase, etc. Once the steps are built it's trivial to combine them in the way you need to get the performance results you want. 

## Creating a script

Like in the example above, we do three basic actions to create a script. 

(1) Instantiate the JMeterRunner with a test plan name:

```java
 JMeterRunner jmeter = new JMeterRunner("Login");
```
(2) Add one or more steps (which can then also have nested steps)

```java
jmeter.addStep(ThreadGroupSteps.createThreadGroup("Login Test")
                                .addReportableStep(HTTPSteps.login()));
```
(3) run the jmeter script

```java
jmeter.run();
```

## JMeter Steps

These directly correspond to steps in the JMeter UI. You can add steps directly or add thread groups

## Thread Group steps

These also directly correspond to JMeter Thread Groups and you can add steps or other thread groups just as you would in the JMeter application

## Steps vs. Reportable Steps 

JMeter tends to report everything to one output file, but for our purposes we wanted the ability to selectively decide whether we cared about a time measurement for each step in a threadgroup. So while we still provide all of the summary output, we filter that output into a secondary reportable-summary file that only contains the measured steps we care about. 

```java
Threadgroup.addStep() // perform the step but only show in the summary file
Threadgroup.addReportableStep() // perform the step and show in the summary and reportable-summary file. 
```

## Supported JMeter methods

We've only built out what we need, but it's easy to add support for JMeter steps and we'd love to get contributions. 

[Currently supported steps](https://github.com/lithiumtech/mineraloil-jmeter/tree/master/src/main/java/com/lithium/mineraloil/jmeter/test_elements)

## Built-in assumptions

We assume that you want a cookie manager to maintain a session throughout your test case so that's added in automatically - you don't have to maintain it in your test steps. 

The steps for adding a results collector and summary report are also automatically added so we're always collecting the right level of output. 

## JMeter extends Observable

The jmeter runner extends Observable so will allow observers to attach and will notify them on test start and stop, giving them the name of the test plan being executed. 

## Example Test with Steps Readme and Code
Check out the readme in the Example directory
(https://github.com/lithiumtech/mineraloil-jmeter/tree/master/src/main/java/example)

## OUTPUT
Mineraloil-jmeter will output .jtl and .jmx files.
Both the .jtx and .jtl files can be directly opened with JMeter UI if needed.
The .jtl files are also recognizable be the Jenkins Performance Plugin which displays graphs based on the data.
All output files will be found in the target/jmeter directory


