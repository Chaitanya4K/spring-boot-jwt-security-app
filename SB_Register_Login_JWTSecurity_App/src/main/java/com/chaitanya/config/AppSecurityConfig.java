package com.chaitanya.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.chaitanya.service.CustomerService;

import lombok.SneakyThrows;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
	@Autowired
	private CustomerService customerService;
	
	@Bean
	public BCryptPasswordEncoder pwdEncoder() {

		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

		http.csrf(AbstractHttpConfigurer::disable)
		
			.authorizeHttpRequests(req -> {
				req								
				.requestMatchers("/register","/login")
				.permitAll()
				.anyRequest()
				.authenticated();
			}) 	
		 .authenticationProvider(authProvider());
		
			
		return http.build();

	}
	

	
	// load customer record from DB
	
	@Bean
	@SneakyThrows
    public AuthenticationProvider authProvider() {
       
		DaoAuthenticationProvider authProvider = 
        		new DaoAuthenticationProvider(customerService);

        authProvider.setPasswordEncoder(pwdEncoder());
    //   authProvider.setUserDetailsService(customerService);

        return authProvider;
    }
	
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
		
	}
	
}
