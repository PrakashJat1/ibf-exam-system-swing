package com.prakash.service;

import com.prakash.entity.User;
import com.prakash.repository.UserRepository;

public class UserService {

    UserRepository userRepository;
    public UserService(){
        this.userRepository = new UserRepository();
    }

    public String verifyUser(User user)
    {
        return userRepository.verifyUser(user);
    }

    public boolean saveUser(User user)
    {
        return userRepository.saveUser(user);
    }

}
