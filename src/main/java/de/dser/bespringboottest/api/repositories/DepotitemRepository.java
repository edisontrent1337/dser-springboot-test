package de.dser.bespringboottest.api.repositories;

import de.dser.bespringboottest.entities.Depotitem;
import de.dser.bespringboottest.entities.DepotitemDelivery;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepotitemRepository extends CrudRepository<Depotitem, Long> {
	List<Depotitem> findAllByDepotitemDelivery(DepotitemDelivery depotitemDelivery);

	List<Depotitem> findAllByDepotitemDeliveryOrderByWkn(DepotitemDelivery depotitemDelivery);
}
