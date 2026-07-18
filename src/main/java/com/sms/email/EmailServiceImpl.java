package com.sms.email;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender mailSender;


    @Value("${app.mail.from}")
    private String fromEmail;


    @Value("${app.mail.reset-password-url}")
    private String resetPasswordUrl;


    // SMTP Debug purpose

    @Value("${spring.mail.host}")
    private String host;


    @Value("${spring.mail.port}")
    private String port;


    @Value("${spring.mail.username}")
    private String username;


    @Value("${spring.mail.password}")
    private String password;



    /*
     * Application start hote hi email configuration verify karega
     */
    @PostConstruct
    public void emailConfigurationCheck() {

        log.info("========== EMAIL CONFIGURATION CHECK ==========");

        log.info("SMTP Host : {}", host);
        log.info("SMTP Port : {}", port);
        log.info("SMTP Username : {}", username);

        if(password != null) {
            log.info("SMTP Password Loaded : YES");
            log.info("Password Length : {}", password.length());
        }
        else {
            log.error("SMTP Password Loaded : NO");
        }

        log.info("Mail From : {}", fromEmail);
        log.info("Reset URL : {}", resetPasswordUrl);

        log.info("==============================================");
    }



    @Override
    public void sendPasswordResetEmail(
            String toEmail,
            String token) {


        log.info("========== PASSWORD RESET EMAIL ==========");


        log.info("Receiver Email : {}", toEmail);

        log.info("Token Generated : {}", token);



        String resetLink = resetPasswordUrl + token;


        log.info("Reset Link : {}", resetLink);



        SimpleMailMessage message =
                new SimpleMailMessage();



        message.setFrom(fromEmail);

        message.setTo(toEmail);


        message.setSubject(
                "Password Reset Request - University Management System"
        );



        message.setText("""
                
                Hello,

                We received a request to reset your password.

                Please click the link below to reset your password:

                %s

                This link will expire in 15 minutes.

                If you did not request this password reset,
                please ignore this email.

                Regards,
                University Management System
                
                """.formatted(resetLink));



        try {


            log.info("Sending email through SMTP server...");


            mailSender.send(message);



            log.info(
                "Password reset email sent successfully to {}",
                toEmail
            );


        }
        catch (MailException exception) {


            log.error(
                "Email sending failed for {}",
                toEmail
            );


            log.error(
                "Mail Error : {}",
                exception.getMessage()
            );


            throw exception;
        }


        log.info("==========================================");
    }

}