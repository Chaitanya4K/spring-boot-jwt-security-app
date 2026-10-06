package com.chaitanya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chaitanya.entity.Customer;
import com.chaitanya.service.CustomerService;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AuthenticationManager authManager;

	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer c) {

		boolean status = customerService.saveCustomer(c);

		if (status) {
			return new ResponseEntity<>("Customer Registered Successfully", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	} 
	 
	@PostMapping("/login")
	public ResponseEntity<String> formLogin(
	        @RequestBody Customer c){

	    try {

	        UsernamePasswordAuthenticationToken token =
	                new UsernamePasswordAuthenticationToken(
	                        c.getEmail(),
	                        c.getPwd()
	                        );
// verify login details valid or not
	        Authentication authenticate =
	                authManager.authenticate(token);

	        if(authenticate.isAuthenticated()) {
	            return new ResponseEntity<>(
	                    "WELCOME",
	                    HttpStatus.OK);
	        }

	    } catch (Exception e) {

	        return new ResponseEntity<>(
	                "Invalid Credentials",
	                HttpStatus.UNAUTHORIZED);
	    }

	    return new ResponseEntity<>(
	            "Login Failed",
	            HttpStatus.BAD_REQUEST);
	}
	

	}
