package com.example.UserService.dao;

import com.example.UserService.entity.UserDetails;
import com.example.UserService.exceptions.CustomException;
import com.example.UserService.repository.UserDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class UserDetailsDao {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsDao(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Transactional
    public void save(UserDetails userDetails) {
        try {
            userDetailsRepository.save(userDetails);
        } catch (Exception e) {
            log.error(e, e);
            throw new CustomException("Unable to save userDetails", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<UserDetails> getUserByEmailId(String email) {
        try {
            return userDetailsRepository.getByEmail(email);
        } catch (Exception e) {
            log.error(e, e);
            throw new CustomException("Unable to get userDetails by emailId", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<UserDetails> getUserByUserId(Long userId) {
        try {
            return userDetailsRepository.getByUserId(userId);
        } catch (Exception e) {
            log.error(e, e);
            throw new CustomException("Unable to get userDetails by userId", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
