package com.jambit.les.tutorial.rest.integration;

import com.jambit.les.tutorial.rest.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class World {

    @Getter
    @Setter
    private String userEmail;

    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private ResponseEntity response;

    @Getter
    private String performerUsername;

    @Getter
    private String performerPassword;

    public void setPerformerCredentials(final String username, final String password) {
        this.performerUsername = username;
        this.performerPassword = password;
    }
}
