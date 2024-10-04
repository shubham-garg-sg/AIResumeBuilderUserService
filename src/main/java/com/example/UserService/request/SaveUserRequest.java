package com.example.UserService.request;

public record SaveUserRequest(String email, String name, String profileImage, String phoneNumber) {
}
