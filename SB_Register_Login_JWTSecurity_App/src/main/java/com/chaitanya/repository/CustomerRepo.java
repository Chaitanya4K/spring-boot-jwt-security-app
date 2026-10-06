package com.chaitanya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chaitanya.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	public Customer findByEmail(String email);

}
