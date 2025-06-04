package com.autotec.backend.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.autotec.backend.model.User;
import com.autotec.backend.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @MutationMapping
    public User createUser(
        @Argument String name,
        @Argument String email,
        @Argument String type
    ) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setType(type);
        return userService.createUser(user);
    }

    @MutationMapping
    public User createUserIfNotExists(
        @Argument String sub, 
        @Argument String name, 
        @Argument String email, 
        @Argument String type) {
        return userService.createUserIfNotExists(sub, name, email, type);
    }

    @QueryMapping
    public User getUserById(@Argument String id) {
        return userService.getUserById(id).orElse(null);
    }

    @QueryMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @MutationMapping
    public Boolean deleteUser(@Argument String id) {
        return userService.deleteUser(id);
    }

    @MutationMapping
    public User updateUser(
        @Argument String id,
        @Argument String name,
        @Argument String email,
        @Argument String type
    ) {
        return userService.updateUser(id, name, email, type);
    }
}
