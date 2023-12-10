package com.springboot.multi.tenant.example.controller;

import com.springboot.multi.tenant.example.repository.user.UserModel;
import com.springboot.multi.tenant.example.service.CreateUserService;
import com.springboot.multi.tenant.example.service.GetUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers/{customerID}/users")
public class UserController {

    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private GetUserService getUserService;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        return ResponseEntity.status(200).body(createUserService.createUser(userModel));
    }

    @GetMapping("/{userID}")
    public ResponseEntity<UserModel> getUserByID(@PathVariable("userID") Long userID) throws Exception {
        return ResponseEntity.status(200).body(getUserService.getUserByID(userID));
    }
}
