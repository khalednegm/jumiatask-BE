package com.jumiatask.serviceHandler;

import java.util.List;

import com.jumiatask.dtos.CustomerDTO;

public interface CustomerHandler {
	List<CustomerDTO> getPhoneNumbers(List<String> countries, Boolean isValid);
}
