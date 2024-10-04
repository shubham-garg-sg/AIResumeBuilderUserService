package com.example.UserService.repository;

import com.example.UserService.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    Optional<UserDetails> getByEmail(String email);
    Optional<UserDetails> getByUserId(Long userId);
}
