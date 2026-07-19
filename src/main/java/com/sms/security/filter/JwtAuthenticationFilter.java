package com.sms.security.filter;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sms.security.service.CustomUserDetailsService;
import com.sms.security.service.JwtBlacklistService;
import com.sms.security.service.JwtService;

import com.sms.util.SecurityResponseWriter;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import lombok.RequiredArgsConstructor;



@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter 
        extends OncePerRequestFilter {



    private static final Logger log =
            LoggerFactory.getLogger(
                    JwtAuthenticationFilter.class
            );



    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    private final JwtBlacklistService jwtBlacklistService;


    @Override
    protected void doFilterInternal(

            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain


    ) throws ServletException, IOException {


        try {


            String authHeader =
                    request.getHeader("Authorization");


            
            /*
             * No JWT Token
             */
            if(authHeader == null ||
                    !authHeader.startsWith("Bearer ")) {


                filterChain.doFilter(
                        request,
                        response
                );

                return;

            }




            String token =
                    authHeader.substring(7);

            


            log.debug(
                    "JWT Token received"
            );




            /*
             * Check Logout Token
             */

            if(jwtBlacklistService
                    .isBlacklisted(token)) {


                log.warn(
                    "Blocked blacklisted token"
                );


                SecurityResponseWriter.sendError(
                        response,
                        "Token expired or logged out"
                );

                return;

            }




            String username =
                    jwtService.extractUsername(
                            token
                    );



            log.debug(
                    "JWT username : {}",
                    username
            );





            if(username != null &&

              SecurityContextHolder
              .getContext()
              .getAuthentication()==null) {



                UserDetails userDetails =

                        userDetailsService
                        .loadUserByUsername(
                                username
                        );




                if(jwtService.isTokenValid(

                        token,

                        userDetails.getUsername()

                )) {



                    UsernamePasswordAuthenticationToken authentication =


                            new UsernamePasswordAuthenticationToken(


                                    userDetails,


                                    null,


                                    userDetails
                                    .getAuthorities()

                            );




                    SecurityContextHolder
                    .getContext()
                    .setAuthentication(
                            authentication
                    );



                    log.debug(
                        "Authentication set successfully for {}",
                        username
                    );


                }

                else {


                    log.warn(
                        "Invalid JWT token for {}",
                        username
                    );

                }

            }


        }


        catch(JwtException e) {


            log.error(
                    "JWT exception : {}",
                    e.getMessage()
            );


            SecurityResponseWriter.sendError(
                    response,
                    "Token expired or logged out"
            );

            return;

        }


        catch(Exception e) {


            log.error(
                    "JWT authentication failed",
                    e
            );


            SecurityResponseWriter.sendError(
                    response,
                    "Token expired or logged out"
            );

            return;

        }



        filterChain.doFilter(
                request,
                response
        );

    }


}