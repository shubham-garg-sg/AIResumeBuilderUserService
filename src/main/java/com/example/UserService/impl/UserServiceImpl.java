package com.example.UserService.impl;

import com.example.UserService.dao.UserDetailsDao;
import com.example.UserService.entity.ReferralCodes;
import com.example.UserService.entity.UserDetails;
import com.example.UserService.enums.StatusCodeEnum;
import com.example.UserService.exceptions.CustomException;
import com.example.UserService.request.SaveUserRequest;
import com.example.UserService.request.UpdateUserRequest;
import com.example.UserService.response.UserServiceResponse;
import com.example.UserService.service.UserService;
import com.example.UserService.util.ReferralCodeGenerationUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserDetailsDao userDetailsDao;

    public UserServiceImpl(UserDetailsDao userDetailsDao) {
        this.userDetailsDao = userDetailsDao;
    }

    @Override
    public UserServiceResponse saveUser(SaveUserRequest saveUserRequest) {
        try {
            // Validate Request
            validateRequest(saveUserRequest);

            // Check if user already exists, if yes then update only the last login
            Optional<UserDetails> existingUser = userDetailsDao.getUserByEmailId(saveUserRequest.email());
            if (existingUser.isPresent()) {
                log.debug("User with this emailId already exists : {}", saveUserRequest.email());
                existingUser.get().setLastLogin(LocalDateTime.now());
                userDetailsDao.save(existingUser.get());
                return new UserServiceResponse(StatusCodeEnum.UD200.getStatusCode(), "User details uploaded successfully");
            }

            // Create ReferralCodes Record
            ReferralCodes referralCodes = getReferralCodesRecord(saveUserRequest);

            //Create UserDetails Record
            UserDetails userDetails = new UserDetails();
            userDetails.setEmail(saveUserRequest.email());
            userDetails.setName(saveUserRequest.name());
            userDetails.setProfileImage(saveUserRequest.profileImage());
            userDetails.setPhoneNumber(saveUserRequest.phoneNumber());
            userDetails.setIsActive(true);
            userDetails.setLastLogin(LocalDateTime.now());

            referralCodes.setUserDetails(userDetails);
            userDetails.setReferralCode(referralCodes);

            userDetailsDao.save(userDetails);

            return new UserServiceResponse(StatusCodeEnum.UD200.getStatusCode(), "User details uploaded successfully");
        } catch (CustomException ce) {
            throw ce;
        } catch (Exception e) {
            log.error(e, e);
            throw new CustomException(e.getMessage(), e, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserServiceResponse update(UpdateUserRequest updateUserRequest) {
        try {
            // Validate Request
            validateUpdateUserRequest(updateUserRequest);

            // Fetch user details from userId;
            Optional<UserDetails> userDetailsOptional = userDetailsDao.getUserByUserId(updateUserRequest.userId());
            if (userDetailsOptional.isEmpty()) {
                log.error("User with this userId {} does not exist ", updateUserRequest.userId());
                throw new CustomException("User does not exist", HttpStatus.BAD_REQUEST);
            }

            // Update user details
            UserDetails userDetails = userDetailsOptional.get();
            updateUserDetails(userDetails, updateUserRequest);

            // Save updated record in db
            userDetailsDao.save(userDetails);

            return new UserServiceResponse(StatusCodeEnum.UD200.getStatusCode(), "User details updated successfully");
        } catch (CustomException ce) {
            log.error(ce, ce);
            throw ce;
        } catch (Exception e) {
            log.error(e, e);
            throw new CustomException(e.getMessage(), e, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserDetails getUserDetails(String email) {
        try {
            Optional<UserDetails> userDetails = userDetailsDao.getUserByEmailId(email);
            if (userDetails.isEmpty()) {
                throw new CustomException("User with this emailId does not exist", HttpStatus.BAD_REQUEST);
            }
            return userDetails.get();
        } catch (CustomException ce) {
            log.error(ce, ce);
            throw ce;
        } catch (Exception e) {
            log.error(e, e);
            throw new CustomException(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateUserDetails(UserDetails userDetails, UpdateUserRequest updateUserRequest) {
        if (StringUtils.isNotBlank(updateUserRequest.name())) {
            userDetails.setName(updateUserRequest.name());
        }
        if (StringUtils.isNotBlank(updateUserRequest.phoneNumber())) {
            userDetails.setPhoneNumber(updateUserRequest.phoneNumber());
        }
        if (StringUtils.isNotBlank(updateUserRequest.profileImage())) {
            userDetails.setProfileImage(updateUserRequest.profileImage());
        }
    }

    private void validateUpdateUserRequest(UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.userId() == null || (StringUtils.isBlank(updateUserRequest.name()) &&
                StringUtils.isBlank(updateUserRequest.phoneNumber()) && StringUtils.isBlank(updateUserRequest.profileImage()))) {
            throw new CustomException("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }

    private ReferralCodes getReferralCodesRecord(SaveUserRequest saveUserRequest) {
        ReferralCodes referralCodes = new ReferralCodes();
        referralCodes.setReferralCode(ReferralCodeGenerationUtil.generateReferralCodeFromEmail(saveUserRequest.email()));
        referralCodes.setIsActive(true);
        return referralCodes;
    }


    private void validateRequest(SaveUserRequest saveUserRequest) {
        if (saveUserRequest == null || saveUserRequest.email() == null || saveUserRequest.name() == null) {
            log.debug("Bad request");
            throw new CustomException("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
