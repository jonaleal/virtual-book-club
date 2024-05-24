package com.udea.bookclub.security;

import com.udea.bookclub.repositories.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final IUserRepository userRepository;

    public SecurityConfig(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                //Allowing public access to bookclubs list
                                HttpMethod.GET, "api/v1/book-club/").permitAll()
                        .requestMatchers(
                                //Allowing public access to user registration
                                HttpMethod.POST, "api/v1/user/").permitAll()
                        .requestMatchers(
                                "api/v1/login", "/api/v1/logout"
                                //Allowing public access to api-docs
                                , "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api-docs").permitAll()
                        .requestMatchers("api/v1/**", "**").permitAll()
                        .anyRequest().authenticated()
                )

                .logout((logout) -> logout.logoutUrl("api/v1/logout"))

                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<com.udea.bookclub.models.User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            return User.withUsername(user.get().getUsername())
                    .password(user.get().getPassword())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }
}


