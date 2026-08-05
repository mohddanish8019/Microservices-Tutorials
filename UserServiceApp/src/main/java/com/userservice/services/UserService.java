package com.userservice.services;

import java.util.List;

import com.userservice.entities.User;

public interface UserService {
    // create user
    User createuser(User user);
    // get all users

    List<User> getaAllUsers();

    // get single user
    User getUserById(String id);

    // delete user
    String deleteUser(String id);

    // update user

    User updateUser(User user);

}
