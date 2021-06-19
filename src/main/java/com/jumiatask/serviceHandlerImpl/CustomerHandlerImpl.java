package com.jumiatask.serviceHandlerImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumiatask.dtos.CustomerDTO;
import com.jumiatask.entities.Customer;
import com.jumiatask.exception.InvalidInputException;
import com.jumiatask.repositories.CustomerRepository;
import com.jumiatask.serviceHandler.CustomerHandler;

@Service
@Transactional(readOnly = true)
public class CustomerHandlerImpl implements CustomerHandler {
	
	private static final String CAMERON = "cameroon";
	private static final String ETHIOPIA = "ethiopia";
	private static final String MOROCCO = "morocco";
	private static final String MOZAMBIQUE = "mozambique";
	private static final String UGANDA = "uganda";
	
	private static final String CAMERON_REGEX = "\\(237\\)\\ ?[2368]\\d{7,8}$";
	private static final String ETHIOPIA_REGEX = "\\(251\\)\\ ?[1-59]\\d{8}$";
	private static final String MOROCCO_REGEX = "\\(212\\)\\ ?[5-9]\\d{8}$";
	private static final String MOZAMBIQUE_REGEX = "\\(258\\)\\ ?[28]\\d{7,8}$";
	private static final String UGANDA_REGEX = "\\(256\\)\\ ?\\d{9}$";

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<CustomerDTO> getPhoneNumbers(List<String> countries, Boolean isValid) {
		List<Customer> customers;
		Set<String> countriesSet = new HashSet<>(Arrays.asList(CAMERON, ETHIOPIA, MOROCCO, MOZAMBIQUE, UGANDA));
		
		// Check incoming list of countries
		if(!isValidInput(countries, countriesSet)) {
			throw new InvalidInputException();
		}
		
		customers = customerRepository.findAll();
		
		// Check if country filter is present, but state is valid
		if(isInputCountriesContainsValue(countries) && (isValid == null  || isValid)) {
			customers = getFilteredCustomersByCountry(customers, countries);
		}
		
		// Check if country filter is present and state is not valid
		if(isInputCountriesContainsValue(countries) && (isValid != null  && !isValid) ) {
			customers = getFilteredCustomersByCountryAndInValid(customers, countries);
		}
		
		// Check if country filter not present and state is not valid
		if(!isInputCountriesContainsValue(countries) && (isValid != null  && !isValid) ) {
			customers = getInValidPhoneNumbers(customers);
		}
		
		// Map from entity to dto and return result
		return customers.stream().map(m -> mapFromEntityToDTO(m)).collect(Collectors.toList());
	}
	
	private List<Customer> getFilteredCustomersByCountry(List<Customer> customers, List<String> countries) {
		return customers.stream().filter((c) -> filterByCountry(c, countries)).collect(Collectors.toList());
	}
	
	private List<Customer> getFilteredCustomersByCountryAndInValid(List<Customer> customers, List<String> countries) {
		return customers.stream().filter((c) -> filterByCountry(c, countries)|| filterByInValidPhoneNumber(c) ).collect(Collectors.toList());
	}
	
	private List<Customer> getInValidPhoneNumbers(List<Customer> customers) {
		return customers.stream().filter((c) -> filterByInValidPhoneNumber(c)).collect(Collectors.toList());
	}
	
	private Boolean filterByCountry(Customer customer, List<String> countries) {
		for(String country: countries) {
			if(country.toLowerCase().equals(CAMERON) && customer.getPhone().matches(CAMERON_REGEX)) {
				return true;
			}
			if(country.toLowerCase().equals(ETHIOPIA) && customer.getPhone().matches(ETHIOPIA_REGEX)) {
				return true;
			}
			if(country.toLowerCase().equals(MOROCCO) && customer.getPhone().matches(MOROCCO_REGEX)) {
				return true;
			}
			if(country.toLowerCase().equals(MOZAMBIQUE) && customer.getPhone().matches(MOZAMBIQUE_REGEX)) {
				return true;
			}
			if(country.toLowerCase().equals(UGANDA) && customer.getPhone().matches(UGANDA_REGEX)) {
				return true;
			}
		}
		return false;
	}
	
	private Boolean filterByInValidPhoneNumber(Customer customer) {
		if(!customer.getPhone().matches(CAMERON_REGEX) && !customer.getPhone().matches(ETHIOPIA_REGEX) && !customer.getPhone().matches(MOROCCO_REGEX) 
				&& !customer.getPhone().matches(MOZAMBIQUE_REGEX) && !customer.getPhone().matches(UGANDA_REGEX)) {
			return true;
		}
		return false;
	}
	
	private boolean isValidInput(List<String> countries, Set<String> countriesSet) {		
		if(isInputCountriesContainsValue(countries)) {			
			// Check if input countries are valid
			return countries.stream().anyMatch((c) -> countriesSet.contains(c.toLowerCase()));
		}
		return true;
	}
	
	private boolean isInputCountriesContainsValue(List<String> countries) {
		return countries != null && !countries.isEmpty();
	}
	
	private CustomerDTO mapFromEntityToDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomertId(customer.getCustomerId());
		customerDTO.setCustomerName(customer.getCustomerName());
		customerDTO.setPhone(customer.getPhone());
		return customerDTO;
	}

}