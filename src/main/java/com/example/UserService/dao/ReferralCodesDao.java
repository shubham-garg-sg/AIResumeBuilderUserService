package com.example.UserService.dao;

import com.example.UserService.entity.ReferralCodes;
import com.example.UserService.exceptions.CustomException;
import com.example.UserService.repository.ReferralCodesRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class ReferralCodesDao {

    private final ReferralCodesRepository referralCodesRepository;

    public ReferralCodesDao(ReferralCodesRepository referralCodesRepository) {
        this.referralCodesRepository = referralCodesRepository;
    }

    public Optional<ReferralCodes> getByReferral(String referral) {
        try {
            return referralCodesRepository.findByReferralCode(referral);
        } catch (Exception e) {
            log.error(e, e);
            throw new CustomException("Exception in getting referral record", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
