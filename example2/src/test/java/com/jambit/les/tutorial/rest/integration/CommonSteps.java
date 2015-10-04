package com.jambit.les.tutorial.rest.integration;

import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@CucumberStepsDefinition
public class CommonSteps {

    @Autowired
    private World world;

    @Then("^(?:I get|the user gets) a (.*) response$")
    public void I_get_a__response(final String statusCode) throws Throwable {
        final ResponseEntity response = world.getResponse();
        assertThat(response.getStatusCode(), is(HttpStatus.valueOf(statusCode)));
    }
}
