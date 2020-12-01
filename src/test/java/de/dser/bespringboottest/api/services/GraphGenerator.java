package de.dser.bespringboottest.api.services;

import de.dser.bespringboottest.api.repositories.*;
import de.dser.bespringboottest.entities.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Component
public class GraphGenerator {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DepotRepository depotRepository;
    @Autowired
    private DepotitemRepository depotitemRepository;
    @Autowired
    private DepotitemDeliveryRepository depotitemDeliveryRepository;

    public Bank createBank() {
        Bank bank = new Bank();
        bank.setBanknumber(123456789);
        bank.setBankName("Testbank");
        return bankRepository.save(bank);
    }

    public Customer createCustomer() {
        return createCustomer(createBank());
    }
    public Customer createCustomer(Bank bank) {
        Customer customer = new Customer();
        customer.setCustomerNumber("0033226644");
        customer.setBank(bank);
        return customerRepository.save(customer);
    }

    public Depot createDepot() {
        return createDepot(createCustomer());
    }

    public Depot createDepot(Customer customer) {
        Depot depot = new Depot();
        depot.setCustomer(customer);
        depot.setDepotnumber("0099545488");
        depot.setOpeningDate(LocalDateTime.now());
        return depotRepository.save(depot);
    }

    public DepotitemDelivery createDepotitemDelivery() {
        return createDepotitemDelivery(createDepot());
    }
    public DepotitemDelivery createDepotitemDelivery(Depot depot) {
        return createDepotitemDelivery(depot, LocalDate.of(2000, Month.JANUARY, 1));
    }
    public DepotitemDelivery createDepotitemDelivery(Depot depot, LocalDate deliveryDate) {
        DepotitemDelivery depotitemDelivery = new DepotitemDelivery();
        depotitemDelivery.setHashValue("hash");
        depotitemDelivery.setDepot(depot);
        depotitemDelivery.setDeliveryDate(deliveryDate);
        return depotitemDeliveryRepository.save(depotitemDelivery);
    }

    public Depotitem createDepotitem() {
        return createDepotitem(createDepotitemDelivery());
    }

    public Depotitem createDepotitem(DepotitemDelivery depotitemDelivery) {
        return createDepotitem(depotitemDelivery, "wkn", BigDecimal.valueOf(23423.2323));
    }

    public Depotitem createDepotitem(DepotitemDelivery depotitemDelivery, String wkn, BigDecimal amount) {
        Depotitem depotitem = new Depotitem();
        depotitem.setWkn(wkn);
        depotitem.setIsin("isin");
        depotitem.setName("cooles papier");
        depotitem.setAmount(amount);
        depotitem.setDepotitemDelivery(depotitemDelivery);
        return depotitemRepository.save(depotitem);
    }


}
