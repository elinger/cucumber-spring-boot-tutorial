package com.jambit.les.tutorial.rest.integration;

import com.google.common.collect.ImmutableList;
import com.jambit.les.tutorial.rest.model.User;
import com.jambit.les.tutorial.rest.model.UserBuilder;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.UUID;

import static org.junit.Assert.assertNotEquals;

@CucumberStepsDefinition
public class UserSteps {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private World world;

    @When("^I create a user$")
    public void I_create_a_user() throws Throwable {
        final ResponseEntity<User> responseEntity = tryToCreateUser();

        saveResponse(responseEntity);
    }

    @Given("^there exists a user$")
    public void there_exists_a_user() throws Throwable {
        final ResponseEntity<User> responseEntity = tryToCreateUser();
        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            throw new CucumberException("Failed to create user");
        }

        saveUser(responseEntity.getBody());
    }

    @When("^I update the users's address$")
    public void I_update_the_users_s_address() throws Throwable {
        final User currentUser = getUser();
        final String newAddress = currentUser.getAddress() + " changed";
        final User user = new UserBuilder().copy(currentUser)
                .withAddress(newAddress)
                .build();

        final ResponseEntity<User> responseEntity = tryToUpdateUser(user);

        saveResponse(responseEntity);
    }

    @When("^I delete the user$")
    public void I_delete_the_user() throws Throwable {
        final ResponseEntity<User> response = restTemplate.exchange(
                IntegrationTestConstants.USERS_URI.concat("/").concat(getUser().getEmail()),
                HttpMethod.DELETE,
                null,
                User.class);

        saveResponse(response);
    }

    @When("^I update the address of a non-existent user$")
    public void I_update_the_address_of_a_non_existent_user() throws Throwable {
        final User nonExistentUser = new UserBuilder().withEmail(UUID.randomUUID().toString()).build();

        final ResponseEntity<User> responseEntity = tryToUpdateUser(nonExistentUser);

        saveResponse(responseEntity);
    }

    @And("^The address is updated$")
    public void The_address_is_updated() throws Throwable {
        final User user = getUser();

        final ResponseEntity<User> responseEntity = tryToGetUser(user.getEmail());
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new CucumberException("User not found");
        }

        assertNotEquals(user.getAddress(), responseEntity.getBody().getAddress());
    }

    public void saveResponse(final ResponseEntity responseEntity) {
        world.setResponse(responseEntity);
    }

    public User getUser() {
        return world.getUser();
    }

    public void saveUser(final User user) {
        world.setUser(user);
    }

    private ResponseEntity<User> tryToUpdateUser(final User user) {
        return restTemplate.exchange(
                IntegrationTestConstants.USERS_URI.concat("/").concat(user.getEmail()),
                HttpMethod.PUT,
                createHttpEntity(user, world.getPerformerUsername(), world.getPerformerPassword()),
                User.class);
    }

    private ResponseEntity<User> tryToCreateUser() {
        final User user = new UserBuilder()
                .withEmail(UUID.randomUUID().toString().concat("@example.com"))
                .withFirstName("John")
                .withPassword("secretPass")
                .withAddress("an address")
                .build();

        return restTemplate.exchange(
                IntegrationTestConstants.USERS_URI,
                HttpMethod.POST,
                createHttpEntity(user, world.getPerformerUsername(), world.getPerformerPassword()),
                User.class);
    }

    private <T> HttpEntity<T> createHttpEntity(final T entity, final String username, final String password) {
        final String auth = username + ":" + password;
        final byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        final String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Authorization", ImmutableList.of(authHeader));
        return new HttpEntity<>(entity, httpHeaders);

    }

    private ResponseEntity<User> tryToGetUser(final String email) {
        return restTemplate.exchange(
                IntegrationTestConstants.USERS_URI.concat("/").concat(email),
                HttpMethod.GET,
                createHttpEntity(null, world.getPerformerUsername(), world.getPerformerPassword()),
                User.class);
    }
}
