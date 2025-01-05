package org.mine.stepdefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class CucumberHooks {

    @Before
    public void beforeScenario() {
        System.out.println("Setting up the test environment...");
        // Additional setup logic (if required)
    }

    @After
    public void afterScenario() {
        System.out.println("Cleaning up the test environment...");
        // Additional teardown logic (if required)
    }
}
