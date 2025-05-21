package com.engeto.genesis.controller;

import com.engeto.genesis.model.User;
import com.engeto.genesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User created = userService.createUser(user);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserByID(
            @PathVariable int id,
            @RequestParam(value = "detail", required = false,
                    defaultValue = "false") boolean detail) {
        try {


        User user = userService.getUserByID(id);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("surname", user.getSurname());

        if (detail) {
            response.put("personID", user.getPersonID());
            response.put("uuid", user.getUuid());
        }
        return ResponseEntity.ok(response);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public List<?> getAllUsers(
            @RequestParam(value = "detail", required = false,
                    defaultValue = "false") boolean detail) {
        List<User> userList = userService.getAllUsers();
        if (detail) {
            return userList;
        } else {
            return userList.stream()
                    .map(user -> {
                        Map<String, Object> simpleUser = new HashMap<>();
                        simpleUser.put("id", user.getId());
                        simpleUser.put("name", user.getName());
                        simpleUser.put("surname", user.getSurname());
                        return simpleUser;
                    })
                    .collect(Collectors.toList());
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (user.getId() == 0 || user.getName() == null ||
                user.getSurname() == null) {
            return ResponseEntity.badRequest().build();
        }
        userService.updateUserNameAndSurname(
                user.getId(), user.getName(), user.getSurname());
        User updatedUser = userService.getUserByID(user.getId());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
