package com.jambit.les.tutorial.rest.integration;

import cucumber.api.java.en.Given;
import cucumber.runtime.CucumberException;
import org.springframework.beans.factory.annotation.Autowired;

@CucumberStepsDefinition
public class SecuritySteps {

    @Autowired
    private World world;

    @Given("^I am a (CUSTOMER|OPERATIONS_MANAGER)$")
    public void I_am_a__(final String role) {
        switch (role) {
            case "CUSTOMER":
                world.setPerformerCredentials("john", "test123");
                break;
            case "OPERATIONS_MANAGER":
                world.setPerformerCredentials("patrik", "test123test123");
                break;
            default:
                throw new CucumberException("Unsupported role");
        }
    }
}
