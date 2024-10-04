package com.example.UserService.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class ReferralCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long referralId;
    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDetails userDetails;
    private String referralCode;
    @CreationTimestamp
    private LocalDateTime issuedAt;
    private Boolean isActive;
}
