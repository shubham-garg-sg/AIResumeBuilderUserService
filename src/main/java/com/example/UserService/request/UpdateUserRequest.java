package com.example.UserService.request;

public record UpdateUserRequest(Long userId, String name, String profileImage, String phoneNumber) {
}
