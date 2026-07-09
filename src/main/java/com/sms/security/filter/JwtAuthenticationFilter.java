package com.sms.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sms.security.service.CustomUserDetailsService;
import com.sms.security.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final CustomUserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {

		this.jwtService = jwtService;

		this.userDetailsService = userDetailsService;

	}

	@Override
	protected void doFilterInternal(

			HttpServletRequest request,

			HttpServletResponse response,

			FilterChain filterChain

	) throws ServletException, IOException {

		System.out.println("===== JWT FILTER START =====");
		String authHeader = request.getHeader("Authorization");
		System.out.println("Authorization Header : " + authHeader);

		String token = null;

		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {

			token = authHeader.substring(7);

			System.out.println("JWT Token Found");
			username = jwtService.extractUsername(token);
			System.out.println("JWT Username : " + username);

		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails =

					userDetailsService.loadUserByUsername(username);

			System.out.println("Authorities : " + userDetails.getAuthorities());

			if (jwtService.isTokenValid(token, userDetails.getUsername())) {

				UsernamePasswordAuthenticationToken authentication =

						new UsernamePasswordAuthenticationToken(

								userDetails,

								null,

								userDetails.getAuthorities()

						);

				SecurityContextHolder.getContext().setAuthentication(authentication);

				System.out.println("Authentication Set Successfully");

			}

		}

		filterChain.doFilter(request, response);

	}

}