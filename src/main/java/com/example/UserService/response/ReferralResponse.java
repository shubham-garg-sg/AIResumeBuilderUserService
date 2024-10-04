package com.example.UserService.response;

public record ReferralResponse(Boolean referralCodeExist, Long referrerUserId, Boolean isSuccess) {
}
