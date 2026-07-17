package com.sms.security.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.security.entity.Role;
import com.sms.security.entity.User;
import com.sms.security.entity.UserStatus;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


	Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    

	boolean existsByRole(Role admin);
	
	Optional<User> findByEmailAndStatus(
	        String email,
	        UserStatus status
	);
    

}