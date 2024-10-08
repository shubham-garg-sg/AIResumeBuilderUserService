package com.example.UserService.controller;

import com.example.UserService.response.ReferralResponse;
import com.example.UserService.service.ReferralService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
public class ReferralController {

    private final ReferralService referralService;

    public ReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @GetMapping("/referral/user-details")
    public ReferralResponse getUserDetailsIfExist(@RequestParam String referralCode) {
        log.debug("Request received to get user details for referralCode : {}", referralCode);
        return referralService.getUserDetailsIfExist(referralCode);
    }
}
