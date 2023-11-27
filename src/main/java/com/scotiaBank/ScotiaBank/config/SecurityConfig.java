package com.scotiaBank.ScotiaBank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.scotiaBank.ScotiaBank.Filter.JwtAuthenticationFilter;
import com.scotiaBank.ScotiaBank.Service.impl.CustomUserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig {
	 private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private  JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private  CustomUserDetailsServiceImpl userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider();
		authprovider.setUserDetailsService(userDetailsService);
		authprovider.setPasswordEncoder(passwordEncoder);
		return authprovider;
	}
    
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		LOG.info(" security filter");
		 http.csrf(csrf -> csrf.disable())
		 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		 .authorizeHttpRequests(authorize ->  authorize
				 .requestMatchers("/api/signin/**").permitAll()
	             .requestMatchers("/api/signup/**").permitAll()
	             .requestMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
	             .requestMatchers(HttpMethod.GET, "/api/**" ).permitAll()
	             .requestMatchers(HttpMethod.POST, "/api/**" ).permitAll()
	             .anyRequest().authenticated()
	             )
		 .authenticationProvider(authenticationProvider())
		 .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
		 return http.build();
		 
	}
	
}
