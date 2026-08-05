package com.userservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.entities.User;
import com.userservice.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    // create user
    @PostMapping("/save")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.createuser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    // get single user
    @GetMapping("/getById/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);

    }

    // get all user
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getaAllUsers());
    }

    @DeleteMapping("/deleteById/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        String message = userService.deleteUser(userId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateUser);
    }

}
