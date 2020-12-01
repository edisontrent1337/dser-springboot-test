package de.dser.bespringboottest.api.services;

import de.dser.bespringboottest.api.controllers.factories.DepotitemDeliveryFactory;
import de.dser.bespringboottest.api.controllers.factories.DepotitemFactory;
import de.dser.bespringboottest.api.repositories.DepotRepository;
import de.dser.bespringboottest.api.repositories.DepotitemDeliveryRepository;
import de.dser.bespringboottest.api.repositories.DepotitemRepository;
import de.dser.bespringboottest.api.services.stubs.dto.DtoDepotitemDelivery;
import de.dser.bespringboottest.api.services.stubs.exception.DeliveryAlreadyExistingException;
import de.dser.bespringboottest.api.services.stubs.exception.NotFoundException;
import de.dser.bespringboottest.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController("/api")
public class DepotitemDeliveryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepotitemDeliveryService.class);

	private final DepotitemDeliveryRepository depotitemDeliveryRepository;
	private final DepotitemRepository depotitemRepository;
	private final DepotRepository depotRepository;
	private final DepotitemDeliveryFactory depotitemDeliveryFactory;
	private final DepotitemFactory depotitemFactory;
	private final BankService bankService;
	private final CustomerService customerService;

	@Autowired
	public DepotitemDeliveryService(DepotitemDeliveryRepository depotitemDeliveryRepository,
									DepotitemRepository depotitemRepository,
									DepotRepository depotRepository,
									DepotitemDeliveryFactory depotitemDeliveryFactory,
									DepotitemFactory depotitemFactory,
									BankService bankService,
									CustomerService customerService) {
		this.depotitemDeliveryRepository = depotitemDeliveryRepository;
		this.depotitemRepository = depotitemRepository;
		this.depotRepository = depotRepository;
		this.depotitemDeliveryFactory = depotitemDeliveryFactory;
		this.depotitemFactory = depotitemFactory;
		this.bankService = bankService;
		this.customerService = customerService;
	}

	@PostMapping("/DepotitemDelivery")
	public DepotitemDelivery createNewDepotitemDelivery(DtoDepotitemDelivery delivery) {
		long depotId = delivery.getDepotId();
		Optional<Depot> depotToUpdate = depotRepository.findById(depotId);
		if (depotToUpdate.isEmpty()) throw new NotFoundException("depot not found: " + depotId);

		List<DepotitemDelivery> allDeliveries = depotitemDeliveryRepository.findAllByDepot(depotToUpdate.get());
		Optional<DepotitemDelivery> deliveryOnSameDate = allDeliveries.stream()
				.filter(depotitemDelivery -> depotitemDelivery.getDeliveryDate().equals(delivery.getDeliveryDate()))
				.findAny();
		if (deliveryOnSameDate.isPresent()) throw new DeliveryAlreadyExistingException();

		DepotitemDelivery deliveryEntity = depotitemDeliveryFactory.createFromDto(delivery, depotToUpdate.get());
		depotitemDeliveryRepository.save(deliveryEntity);

		List<Depotitem> depotitems = depotitemFactory.createDepotitems(delivery.getDtoDepotitems(), deliveryEntity);
		depotitemRepository.saveAll(depotitems);

		return deliveryEntity;
	}

	public List<Depotitem> loadCurrentAmountsOfBankCustomers(long bankId, LocalDate qualifyingDate) {
		Optional<Bank> bankOptional = bankService.loadBank(bankId);
		// If the bank could not be found, return an empty list of Depotitems
		if (bankOptional.isEmpty()) {
			LOGGER.error(String.format("Error loading current amounts of bank customers. The bank %s could not be found.", bankId));
			return Collections.emptyList();
		} else {
			Bank bankEntity = bankOptional.get();
			// Gather all customers.
			List<Customer> bankCustomers = customerService.getAllCustomersForBank(bankEntity);

			List<Depot> depots = new ArrayList<>();
			// Collect depots of all customers.
			bankCustomers.stream().forEach((customer -> {
				depots.addAll(depotRepository.findAllByCustomer(customer));
			}));
			// Find the last DepotitemDelivery that lays before the qualifyingDate for each Depot and collect them.
			List<DepotitemDelivery> depotitemDeliveries = new ArrayList<>();
			depots.stream().forEach((depot -> {
				depotitemDeliveries.addAll(depotitemDeliveryRepository.findFirstByDepotAndDeliveryDateBeforeOrderByDeliveryDateDesc(depot, qualifyingDate));
			}));

			// Gather all Depotitems that belong to the list of DepotitemDeliveries
			List<Depotitem> depotitems = new ArrayList<>();
			depotitemDeliveries.forEach(depotitemDelivery -> {
				depotitems.addAll(depotitemRepository.findAllByDepotitemDeliveryOrderByWkn(depotitemDelivery));
			});
			// Final step: Merge Depotitems with multiple occurrences by creating one DepotItem valued with the sum
			// of the original DepotItems
			Map<String, Depotitem> depotItemMap = new HashMap<>();
			depotitems.forEach(depotitem -> {
				String depotitemWkn = depotitem.getWkn();
				if (!depotItemMap.containsKey(depotitemWkn)) {
					depotItemMap.put(depotitemWkn, depotitem);
				} else {
					Depotitem mergedDepotItem = mergeDepotItems(depotItemMap.get(depotitemWkn), depotitem);
					depotItemMap.put(depotitemWkn, mergedDepotItem);
				}
			});
			return new ArrayList<>(depotItemMap.values());
		}
	}

	private Depotitem mergeDepotItems(Depotitem oldDepotItem, Depotitem newDepotItem) {
		Depotitem result = new Depotitem();
		BigDecimal oldAmount = oldDepotItem.getAmount();
		BigDecimal newAmount = newDepotItem.getAmount();
		BigDecimal sum = BigDecimal.valueOf(oldAmount.doubleValue() + newAmount.doubleValue());
		result.setAmount(sum);
		result.setCreationDate(newDepotItem.getCreationDate());
		result.setWkn(newDepotItem.getWkn());
		result.setDepotitemDelivery(newDepotItem.getDepotitemDelivery());
		return result;
	}
}
