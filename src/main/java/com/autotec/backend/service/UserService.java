package com.autotec.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.autotec.backend.model.User;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Service
public class UserService {
    private final DynamoDbTable<User> userTable;

    public UserService(DynamoDbTable<User> userTable) {
        this.userTable = userTable;
    }

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        userTable.putItem(user);
        return user;
    }

    public User createUserIfNotExists(String sub, String name, String email, String type) {
        Optional<User> existingUser = Optional.ofNullable(userTable.getItem(r -> r.key(k -> k.partitionValue(sub)))); // o busca por email si prefieres
        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        User newUser = new User();
        newUser.setId(sub);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setType(type);
        userTable.putItem(newUser);
        return newUser;
    }


    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(userTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    public List<User> getAllUsers() {
        return userTable.scan().items().stream().toList();
    }

    public User updateUser(String id, String name, String email, String type) {
        User existing = userTable.getItem(r -> r.key(k -> k.partitionValue(id)));
        if(existing == null) return null;

        if (name != null) existing.setName(name);
        if (email != null) existing.setEmail(email);
        if (type != null) existing.setType(type);

        userTable.putItem(existing);
        return existing;
    }

    public boolean deleteUser(String id) {
        User user = userTable.getItem(r -> r.key(k -> k.partitionValue(id)));
        if (user != null) {
            userTable.deleteItem(user);
            return true;
        }
        return false;
    }
}
