package com.prakash.controller;

import com.prakash.entity.User;
import com.prakash.service.UserService;

public class UserController {

    UserService userService = new UserService();

    public String verifyUser(User user)
    {
        return userService.verifyUser(user);
    }

    public boolean saveUser(User user)
    {
        return userService.saveUser(user);
    }
}
