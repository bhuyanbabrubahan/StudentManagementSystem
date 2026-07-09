package com.sms.security.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sms.security.entity.Role;
import com.sms.security.entity.User;
import com.sms.security.entity.UserStatus;
import com.sms.security.repository.UserRepository;



@Component
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    public DataInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }



    @Override
    public void run(String... args) {


        boolean adminExists =
                userRepository.existsByRole(Role.ADMIN);



        if(!adminExists) {


            User admin = new User();


            admin.setEmail(
                    "admin@sms.com"
            );


            admin.setPassword(
                    passwordEncoder.encode(
                            "Admin@123"
                    )
            );


            admin.setRole(
                    Role.ADMIN
            );


            admin.setStatus(
                    UserStatus.ACTIVE
            );



            userRepository.save(admin);



            System.out.println(
                    "Default Admin Created Successfully"
            );

        }

    }

}