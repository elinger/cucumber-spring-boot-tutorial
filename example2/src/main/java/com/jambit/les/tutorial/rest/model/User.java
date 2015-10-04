package com.jambit.les.tutorial.rest.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@Getter
@Setter
@GeneratePojoBuilder(withCopyMethod = true)
@ToString(exclude = "password")
public class User {

    @JsonProperty
    String email;

    @JsonProperty
    String firstName;

    @JsonProperty
    String password;

    @JsonProperty
    String address;
}
