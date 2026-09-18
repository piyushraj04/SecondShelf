package com.secondshelf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.withUsername("piyush01")
                .password("{noop}12345678")
                .roles("BUYER")
                .build();

        UserDetails user2 = User.withUsername("Rahul04")
                .password("{noop}Rahul@1234")
                .roles("SELLER")
                .build();
        return new InMemoryUserDetailsManager(user1,user2);
    }

}
