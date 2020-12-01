package de.dser.bespringboottest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Depot extends EntityBaseClass {

    @ManyToOne(optional = false)
    private Customer customer;
    @Column(nullable = false)
    private String depotnumber;
    private LocalDateTime openingDate;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDepotnumber() {
        return depotnumber;
    }

    public void setDepotnumber(String depotnumber) {
        this.depotnumber = depotnumber;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;
    }
}
