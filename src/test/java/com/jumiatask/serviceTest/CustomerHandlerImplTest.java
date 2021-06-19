package com.jumiatask.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import com.jumiatask.dtos.CustomerDTO;
import com.jumiatask.exception.InvalidInputException;
import com.jumiatask.serviceHandler.CustomerHandler;

@SpringBootTest
public class CustomerHandlerImplTest {
	
	private static final String CAMERON_REGEX = "\\(237\\)\\ ?[2368]\\d{7,8}$";
	private static final String ETHIOPIA_REGEX = "\\(251\\)\\ ?[1-59]\\d{8}$";
	private static final String MOROCCO_REGEX = "\\(212\\)\\ ?[5-9]\\d{8}$";
	private static final String MOZAMBIQUE_REGEX = "\\(258\\)\\ ?[28]\\d{7,8}$";
	private static final String UGANDA_REGEX = "\\(256\\)\\ ?\\d{9}$";
	
	@Autowired
	private CustomerHandler customerHandler;
	
	@Test
	public void testGetPhoneNumbersIfNoInputIsProvided() {
		List<CustomerDTO> customers = customerHandler.getPhoneNumbers(null, null);
		assertEquals(41, customers.size());
	}
	
	@Test
	public void testGetPhoneNumbersIfFilterCountryIsCamerroon() {
		List<String> filteredCountries = Arrays.asList("cameroon");
		List<CustomerDTO> customers = customerHandler.getPhoneNumbers(filteredCountries, null);
		for(CustomerDTO customerDTO: customers) {			
			assertTrue(customerDTO.getPhone().matches(CAMERON_REGEX));
		}
	}
	
	@Test
	public void testGetPhoneNumbersIfFilterCountryIsEthiopia() {
		List<String> filteredCountries = Arrays.asList("ethiopia");
		List<CustomerDTO> customers = customerHandler.getPhoneNumbers(filteredCountries, null);
		for(CustomerDTO customerDTO: customers) {			
			assertTrue(customerDTO.getPhone().matches(ETHIOPIA_REGEX));
		}
	}
	
	@Test
	public void testGetPhoneNumbersIfFilterCountryIsCameronAndEthiopia() {
		List<String> filteredCountries = Arrays.asList("cameroon", "ethiopia");
		List<CustomerDTO> customers = customerHandler.getPhoneNumbers(filteredCountries, null);
		for(CustomerDTO customerDTO: customers) {			
			assertTrue(customerDTO.getPhone().matches(CAMERON_REGEX) || customerDTO.getPhone().matches(ETHIOPIA_REGEX));
		}
	}
	
	@Test
	public void testGetPhoneNumbersIfFilterCountryIsMoroccoAndMozambiqueAndUganda() {
		List<String> filteredCountries = Arrays.asList("morocco", "mozambique", "Uganda");
		List<CustomerDTO> customers = customerHandler.getPhoneNumbers(filteredCountries, null);
		for(CustomerDTO customerDTO: customers) {			
			assertTrue(customerDTO.getPhone().matches(MOROCCO_REGEX) || customerDTO.getPhone().matches(MOZAMBIQUE_REGEX) || customerDTO.getPhone().matches(UGANDA_REGEX));
		}
	}
	
	@Test
	public void testGetPhoneNumbersIfFilterCountryHasCapitalLetters() {
		List<String> filteredCountries = Arrays.asList("Morocco", "mozAMbique", "UGANDA");
		List<CustomerDTO> customers = customerHandler.getPhoneNumbers(filteredCountries, null);
		for(CustomerDTO customerDTO: customers) {			
			assertTrue(customerDTO.getPhone().matches(MOROCCO_REGEX) || customerDTO.getPhone().matches(MOZAMBIQUE_REGEX) || customerDTO.getPhone().matches(UGANDA_REGEX));
		}
	}
	
	@Test
	public void testGetPhoneNumbersIfFilterIsInValidPhones() {
		List<CustomerDTO> customers = customerHandler.getPhoneNumbers(null, false);
		for(CustomerDTO customerDTO: customers) {			
			assertTrue(!customerDTO.getPhone().matches(CAMERON_REGEX) && !customerDTO.getPhone().matches(ETHIOPIA_REGEX) && 
					!customerDTO.getPhone().matches(MOROCCO_REGEX) && !customerDTO.getPhone().matches(MOZAMBIQUE_REGEX) && !customerDTO.getPhone().matches(UGANDA_REGEX));
		}
	}
	
	@Test
	public void testGetPhoneNumbersIfFilterHasCountryAndInValid() {
		List<String> filteredCountries = Arrays.asList("Morocco");
		List<CustomerDTO> customers = customerHandler.getPhoneNumbers(filteredCountries, false);
		for(CustomerDTO customerDTO: customers) {			
			assertTrue(!customerDTO.getPhone().matches(CAMERON_REGEX) && !customerDTO.getPhone().matches(ETHIOPIA_REGEX) && 
					!customerDTO.getPhone().matches(MOZAMBIQUE_REGEX) && !customerDTO.getPhone().matches(UGANDA_REGEX));
		}
	}
	
	@Test
	public void testGetPhoneNumbersIfFilterHasInValidCountry() {
		List<String> filteredCountries = Arrays.asList("turkey");
		List<CustomerDTO> customers = null;
		try {			
			customers = customerHandler.getPhoneNumbers(filteredCountries, true);
		} catch(InvalidInputException e) {
			assertNull(customers);
		}
	}
	
	@Test
	public void testGetPhoneNumbersIfFilterHasValidAndInValidCountry() {
		List<String> filteredCountries = Arrays.asList("morocco","turkey");
		List<CustomerDTO> customers = null;
		try {			
			customers = customerHandler.getPhoneNumbers(filteredCountries, true);
		} catch(InvalidInputException e) {
			assertNull(customers);
		}
	}
}
