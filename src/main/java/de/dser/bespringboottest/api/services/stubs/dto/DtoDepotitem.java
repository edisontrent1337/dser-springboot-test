package de.dser.bespringboottest.api.services.stubs.dto;

import java.math.BigDecimal;

public class DtoDepotitem {

    private String wkn;
    private BigDecimal amount;

    public String getWkn() {
        return wkn;
    }

    public void setWkn(String wkn) {
        this.wkn = wkn;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
