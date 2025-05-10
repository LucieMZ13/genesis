package com.engeto.genesis.controller;

import com.engeto.genesis.model.User;
import com.engeto.genesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserByID(
            @PathVariable int id,
            @RequestParam(value = "detail", required = false,
                    defaultValue = "false") boolean detail) {
        User user = userService.getUserByIDWithDetail(id);

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("surname", user.getSurname());

        if (detail) {
            response.put("personID", user.getPersonID());
            response.put("uuid", user.getUuid());
        }
        return ResponseEntity.ok(response);
    }
}
