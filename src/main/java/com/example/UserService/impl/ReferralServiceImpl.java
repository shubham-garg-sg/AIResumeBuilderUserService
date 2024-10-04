package com.example.UserService.impl;

import com.example.UserService.dao.ReferralCodesDao;
import com.example.UserService.entity.ReferralCodes;
import com.example.UserService.exceptions.CustomException;
import com.example.UserService.response.ReferralResponse;
import com.example.UserService.service.ReferralService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class ReferralServiceImpl implements ReferralService {

    private final ReferralCodesDao referralCodesDao;

    public ReferralServiceImpl(ReferralCodesDao referralCodesDao) {
        this.referralCodesDao = referralCodesDao;
    }

    @Override
    public ReferralResponse getUserDetailsIfExist(String referralCode) {
        try {
            Optional<ReferralCodes> referralCodes = referralCodesDao.getByReferral(referralCode);
            if (referralCodes.isEmpty()) {
                log.error("The given referralCode does not exist for any user : {}", referralCode);
                return new ReferralResponse(false, null, false);
            }
            return new ReferralResponse(true, referralCodes.get().getUserDetails().getUserId(), true);
        } catch (CustomException ce) {
            log.error(ce, ce);
            throw ce;
        }
    }
}
