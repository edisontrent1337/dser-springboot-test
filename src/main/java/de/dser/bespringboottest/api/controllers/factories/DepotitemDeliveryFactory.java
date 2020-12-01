package de.dser.bespringboottest.api.controllers.factories;

import com.google.common.hash.Hashing;
import de.dser.bespringboottest.api.services.stubs.dto.DtoDepotitem;
import de.dser.bespringboottest.api.services.stubs.dto.DtoDepotitemDelivery;
import de.dser.bespringboottest.entities.Depot;
import de.dser.bespringboottest.entities.DepotitemDelivery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepotitemDeliveryFactory {

    public DepotitemDelivery createFromDto(DtoDepotitemDelivery delivery, Depot depot) {
        DepotitemDelivery depotitemDelivery = new DepotitemDelivery();
        depotitemDelivery.setDeliveryDate(delivery.getDeliveryDate());
        depotitemDelivery.setDepot(depot);
        depotitemDelivery.setHashValue(calculateHash(delivery.getDtoDepotitems()));

        return depotitemDelivery;
    }

    private String calculateHash(List<DtoDepotitem> dtoDepotitems) {
        String originalString = dtoDepotitems.
                stream().sorted((d1, d2) -> d1.getWkn().compareTo(d2.getWkn()))
                .map(d -> StringUtils.join(d.getWkn(), d.getAmount().doubleValue()))
                .collect(Collectors.joining());

        return Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();
    }
}
