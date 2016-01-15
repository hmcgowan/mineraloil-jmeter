package example.com.mycompany.myproject.performance.steps;

import com.lithium.mineraloil.jmeter.test_elements.ResponseAssertionElement;
import com.lithium.mineraloil.jmeter.test_elements.ResponseField;
import com.lithium.mineraloil.jmeter.test_elements.ResponsePatternType;
import com.lithium.mineraloil.jmeter.test_elements.ResponseSampleType;

public class AssertionSteps {
    public static ResponseAssertionElement isLoggedIn() {
        return ResponseAssertionElement.builder()
                                       .name("Assert user is logged in")
                                       .responseSampleType(ResponseSampleType.MAIN_SAMPLE)
                                       .responseField(ResponseField.TEXT)
                                       .responsePatternType(ResponsePatternType.CONTAINS)
                                       .not(false)
                                       .testString("Log Out")
                                       .build();
    }
}
