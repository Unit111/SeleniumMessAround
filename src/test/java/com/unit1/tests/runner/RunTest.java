package com.unit1.tests.runner;

/**
 * Created by Unit 1 on 02-Jul-17.
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author vthipperudrappa
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.unit1.tests.testhooks",
                "com.unit1.tests.stepDefs"
        },
        features = {"src/test/resources/features/"},
        plugin = {
                "pretty", "html:target/cucumber-reports",
                "json:target/cucumber-reports/cucumber.json"},
        tags = {"@Wiki"}
)
public class RunTest {
}
