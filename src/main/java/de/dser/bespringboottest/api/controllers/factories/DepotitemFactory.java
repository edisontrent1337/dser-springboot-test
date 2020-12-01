package de.dser.bespringboottest.api.controllers.factories;

import de.dser.bespringboottest.api.services.stubs.dto.DtoDepotitem;
import de.dser.bespringboottest.entities.Depotitem;
import de.dser.bespringboottest.entities.DepotitemDelivery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepotitemFactory {

    public List<Depotitem> createDepotitems(List<DtoDepotitem> depotitems, DepotitemDelivery depotitemDelivery) {
        return depotitems.stream().map(di -> {
            return new DepotitemBuilder()
                    .setWkn(di.getWkn())
                    .setAmount(di.getAmount())
                    .setDepotitemDelivery(depotitemDelivery)
                    .build();
        }).collect(Collectors.toList());
    }
}
