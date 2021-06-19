package com.jumiatask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.jumiatask.api.CustomerRestApi;
import com.jumiatask.dtos.CustomerDTO;
import com.jumiatask.serviceHandler.CustomerHandler;

@RestController
public class CustomerController implements CustomerRestApi {
	
	@Autowired
	CustomerHandler customerHandler;

	@Override
	public ResponseEntity<List<CustomerDTO>> getPhoneNumbers(List<String> countries, Boolean isValid) {
		return ResponseEntity.status(HttpStatus.OK).body(customerHandler.getPhoneNumbers(countries, isValid));
	}

}
