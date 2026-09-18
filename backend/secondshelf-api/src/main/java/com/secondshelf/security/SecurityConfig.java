package com.secondshelf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Public registration APIs
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/register-all-users",
                                "/api/sellers/register",
                                "/api/sellers/register-all-sellers"
                        ).permitAll()

                        // Buyer APIs
                        .requestMatchers("/api/users/**")
                        .hasRole("BUYER")

                        //Anyone who is authenticated can view seller profiles
                        .requestMatchers(HttpMethod.GET,"/api/sellers/**")
                        .authenticated()

                        // Seller APIs
                        .requestMatchers("/api/sellers/**")
                        .hasRole("SELLER")

                        // Everything else
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //For basic
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user1 = User.withUsername("piyush01")
//                .password("{noop}12345678")
//                .roles("BUYER")
//                .build();
//
//        UserDetails user2 = User.withUsername("Rahul04")
//                .password("{noop}Rahul@1234")
//                .roles("SELLER")
//                .build();
//        return new InMemoryUserDetailsManager(user1,user2);
//    }
//for basic
    //For role-base access
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests(auth-> auth
//                        //BUYER APIs
//                        .requestMatchers("/api/users/**")
//                        .hasRole("BUYER")
//                        //SELLER APIs
//                        .requestMatchers("/api/sellers/**")
//                        .hasRole("SELLER")
//
//                        //Everything else requirs authentication
//                        .anyRequest()
//                        .authenticated()
//                )
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
    //for basic
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)
//            throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest()
//                        .authenticated()
//                )
//
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }

}
