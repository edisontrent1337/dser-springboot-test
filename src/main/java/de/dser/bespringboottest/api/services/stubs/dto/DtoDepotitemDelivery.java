package de.dser.bespringboottest.api.services.stubs.dto;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.util.List;

public class DtoDepotitemDelivery {

    private long depotId;
    private LocalDate deliveryDate;
    private List<DtoDepotitem> dtoDepotitems = Lists.newArrayList();

    public long getDepotId() {
        return depotId;
    }

    public void setDepotId(long depotId) {
        this.depotId = depotId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<DtoDepotitem> getDtoDepotitems() {
        return dtoDepotitems;
    }

    public void setDtoDepotitems(List<DtoDepotitem> dtoDepotitems) {
        this.dtoDepotitems = dtoDepotitems;
    }
}
