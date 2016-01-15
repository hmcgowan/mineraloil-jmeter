package example.com.mycompany.myproject.performance.tests;

import com.lithium.mineraloil.jmeter.JMeterRunner;
import com.lithium.mineraloil.jmeter.test_elements.ThreadGroup;
import example.com.mycompany.myproject.performance.steps.HTTPSteps;
import org.junit.Assert;
import org.junit.Test;

public class LoginExample {

    @Test
    public void loginTest() {
        JMeterRunner jMeter = new JMeterRunner("Login");
        jMeter.addStep(ThreadGroup.create("Login Thread Group")
                                  .addReportableStep(HTTPSteps.login()));

        jMeter.run();
        Assert.assertTrue("Test run failed. Error rate: " + jMeter.getSummaryResults().getErrorRate(),
                          jMeter.getSummaryResults().isSuccessful());


    }

}
