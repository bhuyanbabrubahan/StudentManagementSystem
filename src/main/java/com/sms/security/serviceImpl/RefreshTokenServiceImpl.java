package com.sms.security.serviceImpl;


import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.exception.BusinessException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.security.entity.RefreshToken;
import com.sms.security.entity.User;
import com.sms.security.repository.RefreshTokenRepository;
import com.sms.security.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl 
        implements RefreshTokenService {


    private final RefreshTokenRepository refreshTokenRepository;

    private static final Logger log =
	        LoggerFactory.getLogger(AuthenticationServiceImpl.class);


    /*
     * Refresh token validity
     *
     * Production:
     * Access Token  -> short life (15 minutes)
     * Refresh Token -> long life (7 days)
     */
    private static final long REFRESH_TOKEN_DAYS = 7;



    // =====================================
    // CREATE REFRESH TOKEN
    // =====================================

    @Override
    public RefreshToken createRefreshToken(User user) {


        /*
         * Remove old active refresh tokens.
         *
         * Currently:
         * One user = one active refresh token
         *
         * Later:
         * Multi device support can be added.
         */
        refreshTokenRepository.deleteByUser(user);



        RefreshToken refreshToken =
                RefreshToken.builder()

                /*
                 * UUID generates unique random token
                 */
                .token(
                        UUID.randomUUID().toString()
                )


                /*
                 * User mapping
                 */
                .user(user)


                /*
                 * Token expiry
                 */
                .expiryDate(
                        LocalDateTime.now()
                        .plusDays(
                                REFRESH_TOKEN_DAYS
                        )
                )


                .revoked(false)

                .build();



        return refreshTokenRepository.save(refreshToken);

    }





    // =====================================
    // FIND TOKEN
    // =====================================

    @Override
    @Transactional(readOnly = true)
    public RefreshToken findByToken(String token) {


        return refreshTokenRepository
                .findByToken(token)

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Refresh token not found"
                        )
                );

    }





    // =====================================
    // VERIFY EXPIRATION
    // =====================================

    @Override
    public RefreshToken verifyExpiration(
            RefreshToken refreshToken) {



        if(refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {



            /*
             * Delete expired token
             */
            refreshTokenRepository
                    .delete(refreshToken);



            throw new BusinessException(
                    "Refresh token expired. Please login again."
            );

        }



        /*
         * Check revoked token
         */
        if(refreshToken.isRevoked()) {


            throw new BusinessException(
                    "Refresh token revoked."
            );

        }



        return refreshToken;

    }





    // =====================================
    // DELETE USER TOKENS
    // =====================================

    @Override
    public void deleteByUser(User user) {


        refreshTokenRepository
                .deleteByUser(user);

    }





    @Override
    public boolean isExpired(
            RefreshToken refreshToken) {

        /*
         * Token is expired if current time
         * is after expiry date.
         */
        boolean expired = LocalDateTime.now()
                .isAfter(refreshToken.getExpiryDate());

        if (expired) {

            log.warn(
                    "Refresh token expired for user: {}",
                    refreshToken.getUser().getEmail()
            );

        }

        return expired;
    }


}