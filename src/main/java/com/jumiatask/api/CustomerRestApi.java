package com.jumiatask.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jumiatask.dtos.CustomerDTO;

@RequestMapping("/api/v1/customers")
public interface CustomerRestApi {
	
	@GetMapping("/phone")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<CustomerDTO>> getPhoneNumbers(@RequestParam(name = "countries", required = false) List<String> countries, @RequestParam(name = "isValid", required = false) Boolean isValid);
}