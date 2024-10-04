package com.example.UserService.service;

import com.example.UserService.entity.UserDetails;
import com.example.UserService.request.SaveUserRequest;
import com.example.UserService.request.UpdateUserRequest;
import com.example.UserService.response.UserServiceResponse;

public interface UserService {

    UserServiceResponse saveUser(SaveUserRequest saveUserRequest);

    UserServiceResponse update(UpdateUserRequest updateUserRequest);

    UserDetails getUserDetails(String email);
}
