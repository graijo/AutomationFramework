
package org.mine.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/withTableValuePassing.feature", // Path to the feature files
        glue = "org.mine.stepdefinition",        // Package containing step definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"}, // Report generation
        monochrome = true                        // Clean console output
)
public class TestRunnerWithTableValuePassing extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        // Provides Cucumber scenarios to TestNG
        return super.scenarios();
    }
}
