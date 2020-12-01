package de.dser.bespringboottest.api.repositories;

import de.dser.bespringboottest.entities.Bank;
import de.dser.bespringboottest.entities.Customer;
import io.swagger.annotations.Api;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	List<Customer> findAllByBank(Bank bank);
}
