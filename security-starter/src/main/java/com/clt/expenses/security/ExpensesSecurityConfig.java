package com.clt.expenses.security;

import com.clt.expenses.security.ApplicationCredentialManager;
import com.clt.expenses.security.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ExpensesSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile({"test"})
    public MapReactiveUserDetailsService userDetailsManager(PasswordEncoder passwordEncoder){
        UserDetails user =
                User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("USER")
                        .build();
        return new MapReactiveUserDetailsService(user);
    }
    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService(ApplicationCredentialManager applicationCredentialManager){
        return new UserDetailService(applicationCredentialManager);
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/user").permitAll()
                        .anyExchange().authenticated())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
