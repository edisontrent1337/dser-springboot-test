package de.dser.bespringboottest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.print.DocFlavor;
import javax.print.event.PrintJobAttributeEvent;
import java.math.BigDecimal;

@Entity
public class Depotitem extends EntityBaseClass {

    @ManyToOne(optional = false)
    private DepotitemDelivery depotitemDelivery;
    @Column(nullable = false)
    private String wkn;
    private String isin;
    private String name;
    @Column(nullable = false)
    private BigDecimal amount;

    public DepotitemDelivery getDepotitemDelivery() {
        return depotitemDelivery;
    }

    public void setDepotitemDelivery(DepotitemDelivery depotitemDelivery) {
        this.depotitemDelivery = depotitemDelivery;
    }

    public String getWkn() {
        return wkn;
    }

    public void setWkn(String wkn) {
        this.wkn = wkn;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Depotitem{" +
                "depotitemDelivery=" + depotitemDelivery +
                ", wkn='" + wkn + '\'' +
                ", isin='" + isin + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
