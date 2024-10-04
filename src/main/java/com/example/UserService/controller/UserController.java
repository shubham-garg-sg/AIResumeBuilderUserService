package com.example.UserService.controller;

import com.example.UserService.entity.UserDetails;
import com.example.UserService.request.SaveUserRequest;
import com.example.UserService.request.UpdateUserRequest;
import com.example.UserService.response.UserServiceResponse;
import com.example.UserService.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(name = "/api/v1/save/user")
    public ResponseEntity<UserServiceResponse> saveUser(@RequestBody SaveUserRequest saveUserRequest) {
        log.debug("User request received : {}", saveUserRequest);
        UserServiceResponse response = userService.saveUser(saveUserRequest);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(name = "/api/v1/save/user")
    public ResponseEntity<UserServiceResponse> updateUserDetails(@RequestBody UpdateUserRequest updateUserRequest) {
        log.debug("update user request received : {}", updateUserRequest);
        UserServiceResponse response = userService.update(updateUserRequest);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(name = "/api/v1/get/user")
    public ResponseEntity<UserDetails> getUserDetails(@RequestParam String email) {
        log.debug("get user request received for emailId : {}", email);
        UserDetails userDetails = userService.getUserDetails(email);
        return ResponseEntity.ok(userDetails);
    }
}
