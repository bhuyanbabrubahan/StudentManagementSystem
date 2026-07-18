package com.sms.email;


public interface EmailService {


    void sendPasswordResetEmail(
            String email,
            String token
    );


}