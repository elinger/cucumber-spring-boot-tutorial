package com.jambit.les.tutorial.rest.integration;

import com.jambit.les.tutorial.rest.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
@Getter
@Setter
public class World {

    private String userEmail;

    private User user;

    private ResponseEntity response;

}
