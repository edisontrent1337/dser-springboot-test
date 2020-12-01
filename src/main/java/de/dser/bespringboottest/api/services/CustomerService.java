package de.dser.bespringboottest.api.services;

import de.dser.bespringboottest.api.repositories.CustomerRepository;
import de.dser.bespringboottest.entities.Bank;
import de.dser.bespringboottest.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<Customer> getAllCustomersForBank(Bank bank) {
		return customerRepository.findAllByBank(bank);
	}
}
