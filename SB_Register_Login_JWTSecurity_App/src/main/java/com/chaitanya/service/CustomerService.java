package com.chaitanya.service;

import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chaitanya.entity.Customer;
import com.chaitanya.repository.CustomerRepo;

@Service
public class CustomerService implements UserDetailsService {
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	public boolean saveCustomer(Customer c) {
		
		@Nullable
		String encodedPwd = pwdEncoder.encode(c.getPwd());
		c.setPwd(encodedPwd);
		Customer savedCustomer = customerRepo.save(c);
		
		return savedCustomer.getCid() != null;  //if Id is Not equal to Null return true
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Customer c =customerRepo.findByEmail(email);
		
		if (c == null) {
            throw new UsernameNotFoundException(
                    "User not found");
        }
		
		return new User(
				c.getEmail(),
				c.getPwd(),
				Collections.emptyList()
				);
	}
	 
	
}
