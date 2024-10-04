package com.example.UserService.service;

import com.example.UserService.response.ReferralResponse;

public interface ReferralService {

    ReferralResponse getUserDetailsIfExist(String referralCode);
}
