package de.dser.bespringboottest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class DepotitemDelivery extends EntityBaseClass{

    @ManyToOne(optional = false)
    private Depot depot;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    private String hashValue;

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    @Override
    public String toString() {
        return "DepotitemDelivery{" +
                "depot=" + depot +
                ", deliveryDate=" + deliveryDate +
                ", hashValue='" + hashValue + '\'' +
                '}';
    }
}
