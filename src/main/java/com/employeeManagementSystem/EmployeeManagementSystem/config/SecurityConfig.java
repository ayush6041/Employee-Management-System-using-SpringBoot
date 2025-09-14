package com.employeeManagementSystem.EmployeeManagementSystem.config;


import com.employeeManagementSystem.EmployeeManagementSystem.service.AdminService;
import com.employeeManagementSystem.EmployeeManagementSystem.service.CustomUserDetailsService;
import com.employeeManagementSystem.EmployeeManagementSystem.service.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(AdminService adminService, EmployeeService employeeService) {
        return new CustomUserDetailsService(adminService, employeeService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home","/login", "/forgotPassword", "/registerAdmin", "/employee/register")
                        .permitAll() // Public endpoints
                        .requestMatchers("/employee/**").hasRole("EMPLOYEE") // Employee pages
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Admin pages
                        .anyRequest().authenticated() // Other pages
                )
                .addFilterBefore(roleBasedRedirectFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );

        return http.build();
    }

    // Unified Success Handler for Admin and Employee
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = determineRedirectUrl(authentication);
            response.sendRedirect(redirectUrl);
        };
    }

    private String determineRedirectUrl(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return "/";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return "/employee/dashboard";
        }
        return "/login";
    }

    // Unified Failure Handler
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
    }

    // Authentication Provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public OncePerRequestFilter roleBasedRedirectFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(
                    @NonNull HttpServletRequest request,
                    @NonNull HttpServletResponse response,
                    @NonNull FilterChain filterChain
            ) throws IOException, ServletException {
                String path = request.getRequestURI();

                // Allow public endpoints to proceed
                if (path.equals("/login") || path.equals("/forgotPassword") || path.equals("/registerAdmin") || path.startsWith("/employee/register")) {
                    filterChain.doFilter(request, response);
                    return;
                }

                // Check user authentication and roles
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                    if (authentication.getAuthorities().stream()
                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                        if (path.equals("/login")) {
                            response.sendRedirect("/");
                            return;
                        }
                    } else if (authentication.getAuthorities().stream()
                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_EMPLOYEE"))) {
                        if (path.equals("/login")) {
                            response.sendRedirect("/employee/dashboard");
                            return;
                        }
                    }
                }

                filterChain.doFilter(request, response);
            }
        };
    }

}
