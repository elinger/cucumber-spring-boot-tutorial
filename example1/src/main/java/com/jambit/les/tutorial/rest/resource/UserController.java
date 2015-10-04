package com.jambit.les.tutorial.rest.resource;

import com.google.common.collect.Maps;
import com.jambit.les.tutorial.rest.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/users")
@Slf4j
public class UserController {

    /**
     * (email, User)
     */
    private Map<String, User> userMap = Maps.newConcurrentMap();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.debug("Create user: {}", user);

        if (userMap.containsKey(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userMap.put(user.getEmail(), user);


        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{email:.+}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        log.debug("Get user by email: {}", email);

        final User user = userMap.get(email);

        if (null == user) {
            log.warn("User not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{email:.+}")
    public ResponseEntity<User> deleteUser(@PathVariable String email) {
        log.debug("Delete user with email: {}", email);

        final User user = userMap.remove(email);

        if (null == user) {
            log.warn("User not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{email:.+}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
        log.debug("Update user with email: {}", email);

        final User currentUser = userMap.get(email);

        if (null == currentUser) {
            log.warn("User not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userMap.put(email, user);


        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
