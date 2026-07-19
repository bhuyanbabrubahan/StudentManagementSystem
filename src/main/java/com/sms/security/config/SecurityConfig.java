package com.sms.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sms.security.filter.JwtAuthenticationFilter;
import com.sms.security.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


		return http

		        .csrf(csrf -> csrf.disable())

		        .cors(Customizer.withDefaults())

		        .headers(headers -> headers

		                .frameOptions(frame -> frame.sameOrigin())

		                .contentTypeOptions(Customizer.withDefaults())

		                .httpStrictTransportSecurity(hsts ->

		                        hsts.includeSubDomains(true)

		                )

		        )

		        .authorizeHttpRequests(auth -> auth


	            		// ==========================
	            		// PUBLIC APIs
	            		// ==========================

		        		.requestMatchers(

		        		        "/api/auth/register",
		        		        "/api/auth/login",
		        		        "/api/auth/forgot-password",
		        		        "/api/auth/reset-password",
		        		        "/api/auth/refresh-token",

		        		        "/swagger-ui/**",
		        		        "/swagger-ui.html",
		        		        "/v3/api-docs/**",
		        		        "/swagger-resources/**",
		        		        "/webjars/**"

		        		)
		        		.permitAll()

		        		.requestMatchers(
		        		        HttpMethod.OPTIONS,
		        		        "/**"
		        		)
		        		.permitAll()


	                    // ==========================
	                    // DEPARTMENT
	                    // ==========================


	                    // GET Department
	                    .requestMatchers(
	                            HttpMethod.GET,
	                            "/api/departments",
	                            "/api/departments/**"
	                    )
	                    .hasAnyRole(
	                            "ADMIN",
	                            "STUDENT",
	                            "FACULTY"
	                    )


	                    // CREATE UPDATE DELETE Department
	                    .requestMatchers(
	                            HttpMethod.POST,
	                            "/api/departments"
	                    ).hasRole("ADMIN")

	                    .requestMatchers(
	                            HttpMethod.PUT,
	                            "/api/departments/**"
	                    ).hasRole("ADMIN")

	                    .requestMatchers(
	                            HttpMethod.DELETE,
	                            "/api/departments/**"
	                    ).hasRole("ADMIN")



	                    // ==========================
	                    // COURSE
	                    // ==========================


	                    .requestMatchers(
	                            HttpMethod.GET,
	                            "/api/courses",
	                            "/api/courses/**"
	                    )
	                    .hasAnyRole(
	                            "ADMIN",
	                            "STUDENT",
	                            "FACULTY"
	                    )


	                    .requestMatchers(
	                            "/api/courses",
	                            "/api/courses/**"
	                    )
	                    .hasRole("ADMIN")



	                    // ==========================
	                    // STUDENT
	                    // ==========================


	                    .requestMatchers(
	                            "/api/students",
	                            "/api/students/**"
	                    )
	                    .hasAnyRole(
	                            "ADMIN",
	                            "STUDENT",
	                            "FACULTY"
	                    )


	                    // ==========================
	                    // ADMISSION
	                    // ==========================


	                    .requestMatchers(
	                            "/api/admissions",
	                            "/api/admissions/**"
	                    )
	                    .hasAnyRole(
	                            "ADMIN",
	                            "STUDENT"
	                    )



	                    // ==========================
	                    // LOCATION
	                    // ==========================


	                    .requestMatchers(
	                            "/api/countries",
	                            "/api/countries/**",
	                            "/api/states",
	                            "/api/states/**",
	                            "/api/districts",
	                            "/api/districts/**",
	                            "/api/tehsils",
	                            "/api/tehsils/**",
	                            "/api/villages",
	                            "/api/villages/**"
	                    )
	                    .hasAnyRole(
	                            "ADMIN",
	                            "STUDENT"
	                    )
	                    
	                    
	                 // ==========================
	                 // FACULTY
	                 // ==========================


	                 .requestMatchers(
	                         HttpMethod.GET,
	                         "/api/faculties",
	                         "/api/faculties/**"
	                 )
	                 .hasAnyRole(
	                         "ADMIN",
	                         "FACULTY"
	                 )


	                 .requestMatchers(
	                         "/api/faculties",
	                         "/api/faculties/**"
	                 )
	                 .hasRole(
	                         "ADMIN"
	                 )

	                 
	                 
	                 // SEARCH
	                 .requestMatchers(
	                         HttpMethod.POST,
	                         "/api/semesters/search"
	                 )
	                 .hasAnyRole("ADMIN", "FACULTY")

	                 // CREATE
	                 .requestMatchers(
	                         HttpMethod.POST,
	                         "/api/semesters"
	                 )
	                 .hasRole("ADMIN")

	                 // UPDATE
	                 .requestMatchers(
	                         HttpMethod.PUT,
	                         "/api/semesters/**"
	                 )
	                 .hasRole("ADMIN")

	                 // DELETE
	                 .requestMatchers(
	                         HttpMethod.DELETE,
	                         "/api/semesters/**"
	                 )
	                 .hasRole("ADMIN")

	                 // GET
	                 .requestMatchers(
	                         HttpMethod.GET,
	                         "/api/semesters",
	                         "/api/semesters/**"
	                 )
	                 .hasAnyRole("ADMIN", "FACULTY", "STUDENT")
	                 
	                 
	                 
	              // ==========================
	              // FACULTY SUBJECT MAPPING
	              // ==========================

	              // GET
	              .requestMatchers(
	                      HttpMethod.GET,
	                      "/api/faculty-subjects",
	                      "/api/faculty-subjects/**"
	              )
	              .hasAnyRole(
	                      "ADMIN",
	                      "FACULTY"
	              )

	              // SEARCH
	              .requestMatchers(
	                      HttpMethod.POST,
	                      "/api/faculty-subjects/search"
	              )
	              .hasAnyRole(
	                      "ADMIN",
	                      "FACULTY"
	              )

	              // CREATE
	              .requestMatchers(
	                      HttpMethod.POST,
	                      "/api/faculty-subjects"
	              )
	              .hasRole("ADMIN")

	              // UPDATE
	              .requestMatchers(
	                      HttpMethod.PUT,
	                      "/api/faculty-subjects/**"
	              )
	              .hasRole("ADMIN")

	              // DELETE
	              .requestMatchers(
	                      HttpMethod.DELETE,
	                      "/api/faculty-subjects/**"
	              )
	              .hasRole("ADMIN")
	                 
	                 
	              // ==========================
	              // SUBJECT
	              // ==========================


	              // GET SUBJECT
	              // ADMIN + FACULTY + STUDENT

	              .requestMatchers(
	                      HttpMethod.GET,
	                      "/api/subjects",
	                      "/api/subjects/**"
	              )
	              .hasAnyRole(
	                      "ADMIN",
	                      "FACULTY",
	                      "STUDENT"
	              )


	              // CREATE UPDATE DELETE SEARCH
	              // Only ADMIN

	              .requestMatchers(
	                      "/api/subjects",
	                      "/api/subjects/**"
	              )
	              .hasRole(
	                      "ADMIN"
	              )
	                 
	           // ==========================
	           // ATTENDANCE
	           // ==========================


	           // GET
	           .requestMatchers(
	                   HttpMethod.GET,
	                   "/api/attendances",
	                   "/api/attendances/**"
	           )
	           .hasAnyRole(
	                   "ADMIN",
	                   "FACULTY",
	                   "STUDENT"
	           )



	           // SEARCH

	           .requestMatchers(
	                   HttpMethod.POST,
	                   "/api/attendances/search"
	           )
	           .hasAnyRole(
	                   "ADMIN",
	                   "FACULTY"
	           )



	           // CREATE

	           .requestMatchers(
	                   HttpMethod.POST,
	                   "/api/attendances"
	           )
	           .hasAnyRole(
	                   "ADMIN",
	                   "FACULTY"
	           )



	           // UPDATE

	           .requestMatchers(
	                   HttpMethod.PUT,
	                   "/api/attendances/**"
	           )
	           .hasAnyRole(
	                   "ADMIN",
	                   "FACULTY"
	           )



	           // DELETE

	           .requestMatchers(
	                   HttpMethod.DELETE,
	                   "/api/attendances/**"
	           )
	           .hasRole(
	                   "ADMIN"
	           )
	                 
	           
	           
	        // ==========================
	        // EXAM
	        // ==========================


	        // GET
	        .requestMatchers(
	                HttpMethod.GET,
	                "/api/exams",
	                "/api/exams/**"
	        )
	        .hasAnyRole(
	                "ADMIN",
	                "FACULTY",
	                "STUDENT"
	        )



	        // SEARCH

	        .requestMatchers(
	                HttpMethod.POST,
	                "/api/exams/search"
	        )
	        .hasAnyRole(
	                "ADMIN",
	                "FACULTY"
	        )



	        // CREATE

	        .requestMatchers(
	                HttpMethod.POST,
	                "/api/exams"
	        )
	        .hasAnyRole(
	                "ADMIN",
	                "FACULTY"
	        )



	        // UPDATE

	        .requestMatchers(
	                HttpMethod.PUT,
	                "/api/exams/**"
	        )
	        .hasRole(
	                "ADMIN"
	        )

	        
	     // ==========================
	     // RESULT
	     // ==========================

	     // SEARCH
	     .requestMatchers(
	             HttpMethod.POST,
	             "/api/results/search"
	     )
	     .hasAnyRole(
	             "ADMIN",
	             "FACULTY"
	     )

	     // CREATE
	     .requestMatchers(
	             HttpMethod.POST,
	             "/api/results"
	     )
	     .hasRole("ADMIN")

	     // UPDATE
	     .requestMatchers(
	             HttpMethod.PUT,
	             "/api/results/**"
	     )
	     .hasRole("ADMIN")

	     // DELETE
	     .requestMatchers(
	             HttpMethod.DELETE,
	             "/api/results/**"
	     )
	     .hasRole("ADMIN")

	     // GET
	     .requestMatchers(
	             HttpMethod.GET,
	             "/api/results",
	             "/api/results/**"
	     )
	     .hasAnyRole(
	             "ADMIN",
	             "FACULTY",
	             "STUDENT"
	     )
	     
	     
	     
	  // ==========================
	  // STUDENT DOCUMENT
	  // ==========================


	  // Upload

	  .requestMatchers(
	          HttpMethod.POST,
	          "/api/student-documents/upload"
	  )
	  .hasAnyRole(
	          "ADMIN",
	          "STUDENT"
	  )

	     
	     

	        // DELETE

	        .requestMatchers(
	                HttpMethod.DELETE,
	                "/api/exams/**"
	        )
	        .hasRole(
	                "ADMIN"
	        )
	                 

			     	// ==========================
		            // ADMIN
		            // ==========================
		
		            .requestMatchers(
		                    "/api/admin/create"
		            )
		            .hasRole("ADMIN")         

		         // ==========================
		         // PROFILE
		         // ==========================

		         .requestMatchers(
		                 HttpMethod.GET,
		                 "/api/profile/me"
		         )
		         .hasAnyRole(
		                 "ADMIN",
		                 "STUDENT",
		                 "FACULTY"
		         )

		         .requestMatchers(
		        	        HttpMethod.POST,
		        	        "/api/profile/change-password"
		        	)
		        	.hasAnyRole(
		        	        "ADMIN",
		        	        "STUDENT",
		        	        "FACULTY"
		        	)
		        	
		        	
		        	.requestMatchers(
		        	        HttpMethod.POST,
		        	        "/api/auth/logout"
		        	)
		        	.hasAnyRole(
		        	        "ADMIN",
		        	        "STUDENT",
		        	        "FACULTY"
		        	)

		        	.anyRequest()
		        	.authenticated()
	                    
	                    
	                

	       )



	            // JWT Stateless
	            .sessionManagement(session ->

	                    session.sessionCreationPolicy(
	                            SessionCreationPolicy.STATELESS
	                    )

	            )

	            .exceptionHandling(exception ->

	            exception.authenticationEntryPoint(
	                    authenticationEntryPoint
	            )

	            )


	            .authenticationProvider(
	                    authenticationProvider()
	            )


	            .addFilterBefore(
	                    jwtAuthenticationFilter,
	                    UsernamePasswordAuthenticationFilter.class
	            )


	            .build();

	}

	@Bean
	public AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setUserDetailsService(userDetailsService);

		provider.setPasswordEncoder(passwordEncoder);

		return provider;

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

		return configuration.getAuthenticationManager();

	}

}