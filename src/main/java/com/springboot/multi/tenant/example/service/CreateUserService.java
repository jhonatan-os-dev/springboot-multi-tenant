package com.springboot.multi.tenant.example.service;

import com.springboot.multi.tenant.example.repository.user.UserModel;
import com.springboot.multi.tenant.example.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel createUser(UserModel userModel) {

        return userRepository.save(userModel);

    }
}
