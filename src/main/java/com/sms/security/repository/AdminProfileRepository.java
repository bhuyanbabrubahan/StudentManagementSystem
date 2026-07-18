package com.sms.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.enums.RecordStatus;
import com.sms.security.entity.AdminProfile;

public interface AdminProfileRepository
        extends JpaRepository<AdminProfile, Long> {

    Optional<AdminProfile> findByUserIdAndStatus(
            Long userId,
            RecordStatus status
    );

}