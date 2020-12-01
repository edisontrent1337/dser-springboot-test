package de.dser.bespringboottest.api.services;

import de.dser.bespringboottest.api.repositories.DepotitemRepository;
import de.dser.bespringboottest.api.services.stubs.dto.DtoDepotitem;
import de.dser.bespringboottest.api.services.stubs.dto.DtoDepotitemDelivery;
import de.dser.bespringboottest.api.services.stubs.exception.DeliveryAlreadyExistingException;
import de.dser.bespringboottest.entities.*;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
class DepotitemDeliveryServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DepotitemDeliveryServiceTest.class);

	@Autowired
	private DepotitemDeliveryService depotitemDeliveryService;
	@Autowired
	private DepotitemRepository depotitemRepository;
	@Autowired
	private GraphGenerator graphGenerator;

	@Test
	void testCreateNewDepotitemDelivery() {
		Depot depot = graphGenerator.createDepot();

		DtoDepotitemDelivery dtoDelivery = new DtoDepotitemDelivery();
		dtoDelivery.setDeliveryDate(LocalDate.now());
		dtoDelivery.setDtoDepotitems(Lists.newArrayList(
				createDepotitem("766400", BigDecimal.valueOf(23243.23)),
				createDepotitem("PAH004", BigDecimal.valueOf(34.223))
		));
		dtoDelivery.setDepotId(depot.getId());
		DepotitemDelivery result = depotitemDeliveryService.createNewDepotitemDelivery(dtoDelivery);
		LOGGER.info("service executed. doing assertions");

		Assert.assertEquals(dtoDelivery.getDeliveryDate(), result.getDeliveryDate());
		List<Depotitem> allByDepotitemDelivery = depotitemRepository.findAllByDepotitemDelivery(result);
		Assert.assertEquals(2, allByDepotitemDelivery.size());
		Assert.assertTrue(containsWkn("766400", allByDepotitemDelivery));
		Assert.assertTrue(containsWkn("PAH004", allByDepotitemDelivery));
		Assert.assertEquals("ffe059abe3df2db217a6c9923f1bf78a68a7bd92072af0fea84b79466efe0346", result.getHashValue());
	}

	@Test
	void testCreateNewDepotitemDelivery_DeliveryAlreadyExisting() {
		Depot depot = graphGenerator.createDepot();

		DepotitemDelivery existingDepotitemDelivery = graphGenerator.createDepotitemDelivery(depot, LocalDate.of(2000, Month.JANUARY, 1));

		DtoDepotitemDelivery dtoDelivery = new DtoDepotitemDelivery();
		dtoDelivery.setDeliveryDate(existingDepotitemDelivery.getDeliveryDate());//this date is already there!!
		dtoDelivery.setDepotId(depot.getId());


		Assertions.assertThrows(DeliveryAlreadyExistingException.class, () -> {
			depotitemDeliveryService.createNewDepotitemDelivery(dtoDelivery);
		});
	}

	@Test
	void testLoadCurrentAmountsOfBank() {
		Bank bank = graphGenerator.createBank();

		String WKN_1 = "766400";
		String WKN_2 = "PAH004";
		String WKN_3 = "A0DPW0";

		createDepotWithDepotitemDeliveries(bank, Map.of(
				LocalDate.of(2000, Month.JANUARY, 1),
				Lists.newArrayList(
						createDepotitem(WKN_1, BigDecimal.valueOf(20d)),
						createDepotitem(WKN_2, BigDecimal.valueOf(30d))
				),
				LocalDate.of(2000, Month.FEBRUARY, 1),
				Lists.newArrayList(
						createDepotitem(WKN_1, BigDecimal.valueOf(10d)),
						createDepotitem(WKN_2, BigDecimal.valueOf(20d))),
				LocalDate.of(2000, Month.MARCH, 1),
				Lists.newArrayList(
						createDepotitem(WKN_1, BigDecimal.valueOf(40d)),
						createDepotitem(WKN_2, BigDecimal.valueOf(50d)))
		)); /*depot 1*/

		createDepotWithDepotitemDeliveries(bank, Map.of(
				LocalDate.of(1999, Month.DECEMBER, 1),
				Lists.newArrayList(
						createDepotitem(WKN_3, BigDecimal.valueOf(6d)),
						createDepotitem(WKN_2, BigDecimal.valueOf(10))),
				LocalDate.of(2000, Month.MARCH, 1),
				Lists.newArrayList(
						createDepotitem(WKN_2, BigDecimal.valueOf(20))),
				LocalDate.of(2000, Month.APRIL, 1),
				Lists.newArrayList(
						createDepotitem(WKN_3, BigDecimal.valueOf(40d)),
						createDepotitem(WKN_2, BigDecimal.valueOf(50d)))
		)); /*depot 2*/

		createDepotWithDepotitemDeliveries(bank, Map.of(
				LocalDate.of(2001, Month.JUNE, 1),
				Lists.newArrayList(
						createDepotitem(WKN_3, BigDecimal.valueOf(60d)),
						createDepotitem(WKN_2, BigDecimal.valueOf(10d)))
		)); /*depot 3*/


		List<Depotitem> depotitems = depotitemDeliveryService.loadCurrentAmountsOfBankCustomers(bank.getId(), LocalDate.of(2000, Month.FEBRUARY, 20));

		Assert.assertEquals(3, depotitems.size());
		Map<String, BigDecimal> resultAsMap = depotitems.stream().collect(Collectors.toMap(Depotitem::getWkn, Depotitem::getAmount));
		Assert.assertEquals(BigDecimal.valueOf(10d).doubleValue(), resultAsMap.get(WKN_1).doubleValue(), 0.0001);
		Assert.assertEquals(BigDecimal.valueOf(30d).doubleValue(), resultAsMap.get(WKN_2).doubleValue(), 0.0001);
		Assert.assertEquals(BigDecimal.valueOf(6d).doubleValue(), resultAsMap.get(WKN_3).doubleValue(), 0.0001);
	}

	private Depot createDepotWithDepotitemDeliveries(Bank bank, Map<LocalDate, ArrayList<DtoDepotitem>> deliveries) {
		Depot depot = graphGenerator.createDepot(graphGenerator.createCustomer(bank));

		deliveries.forEach((ld, items) -> {
			DepotitemDelivery depotitemDelivery = graphGenerator.createDepotitemDelivery(depot, ld);
			items.stream().forEach(it -> graphGenerator.createDepotitem(depotitemDelivery, it.getWkn(), it.getAmount()));
		});
		return depot;
	}

	private boolean containsWkn(String wkn, List<Depotitem> allByDepotitemDelivery) {
		return allByDepotitemDelivery.stream().anyMatch(p -> p.getWkn().equals(wkn));
	}

	private DtoDepotitem createDepotitem(String wkn, BigDecimal amount) {
		DtoDepotitem dtoDepotitem = new DtoDepotitem();
		dtoDepotitem.setWkn(wkn);
		dtoDepotitem.setAmount(amount);
		return dtoDepotitem;
	}
}
