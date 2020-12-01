package de.dser.bespringboottest.api.repositories;

import de.dser.bespringboottest.entities.Depot;
import de.dser.bespringboottest.entities.DepotitemDelivery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface DepotitemDeliveryRepository extends CrudRepository<DepotitemDelivery, Long> {

	List<DepotitemDelivery> findAllByDepot(Depot depot);

	List<DepotitemDelivery> findFirstByDepotAndDeliveryDateBeforeOrderByDeliveryDateDesc(Depot depot, LocalDate deliveryDate);
}
