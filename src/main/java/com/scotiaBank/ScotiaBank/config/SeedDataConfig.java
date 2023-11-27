package com.scotiaBank.ScotiaBank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.scotiaBank.ScotiaBank.Entity.ERole;
import com.scotiaBank.ScotiaBank.Entity.User;
import com.scotiaBank.ScotiaBank.Service.impl.CustomUserDetailsServiceImpl;
import com.scotiaBank.ScotiaBank.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
public class SeedDataConfig implements CommandLineRunner {

   @Autowired 
   UserRepository userRepository;
   @Autowired 
   PasswordEncoder passwordEncoder;
   @Autowired 
   CustomUserDetailsServiceImpl userService;
   private static final Logger LOG = LoggerFactory.getLogger(SeedDataConfig.class);
    @Override
    public void run(String... args) throws Exception {
        
      if (userRepository.count() == 0) {

        User admin = User
                      .builder()
                      .firstName("admin")
                      .username("admin")
                      .lastName("admin")
                      .email("admin@admin.com")
                      .password(passwordEncoder.encode("password"))
                      .role(ERole.ROLE_ADMIN)
                      .build();

        userService.createNewUser(admin);
        LOG.debug("created ADMIN user - {}", admin);
      }
    }

}