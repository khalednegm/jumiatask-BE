package com.jumiatask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumiatask.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
