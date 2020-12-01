package de.dser.bespringboottest.api.repositories;

import de.dser.bespringboottest.entities.Customer;
import de.dser.bespringboottest.entities.Depot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface DepotRepository extends CrudRepository<Depot, Long> {

	public List<Depot> findAllByCustomer(Customer customer);
}
