package com.example.UserService.repository;

import com.example.UserService.entity.ReferralCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferralCodesRepository extends JpaRepository<ReferralCodes, Long> {

    Optional<ReferralCodes> findByReferralCode(String referralCode);
}
