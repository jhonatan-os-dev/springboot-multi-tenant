package com.springboot.multi.tenant.example.service;

import com.springboot.multi.tenant.example.repository.user.UserModel;
import com.springboot.multi.tenant.example.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Optional;

@Service
public class GetUserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel getUserByID(Long id) throws Exception {
        Optional<UserModel> result = userRepository.findById(id);



        if (result.isEmpty()) {
            throw new Exception("User not found");
        }
        return result.get();

    }
}
