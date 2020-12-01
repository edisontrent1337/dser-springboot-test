package de.dser.bespringboottest.api.controllers.factories;

import de.dser.bespringboottest.entities.Depotitem;
import de.dser.bespringboottest.entities.DepotitemDelivery;

import java.math.BigDecimal;

public class DepotitemBuilder {
    private String wkn;
    private String isin;
    private String name;
    private BigDecimal amount;
    private DepotitemDelivery depotitemDelivery;

    public DepotitemBuilder setDepotitemDelivery(DepotitemDelivery depotitemDelivery) {
        this.depotitemDelivery = depotitemDelivery;
        return this;
    }

    public DepotitemBuilder setWkn(String wkn) {
        this.wkn = wkn;
        return this;
    }

    public DepotitemBuilder setIsin(String isin) {
        this.isin = isin;
        return this;
    }

    public DepotitemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DepotitemBuilder setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Depotitem build() {
        Depotitem depotitem = new Depotitem();
        depotitem.setWkn(wkn);
        depotitem.setIsin(this.isin);
        depotitem.setAmount(this.amount);
        depotitem.setName(this.name);
        depotitem.setDepotitemDelivery(this.depotitemDelivery);
        return depotitem;
    }
}
