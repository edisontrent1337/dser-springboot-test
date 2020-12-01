package de.dser.bespringboottest.api.repositories;

import de.dser.bespringboottest.entities.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource()
public interface BankRepository extends CrudRepository<Bank, Long> {
    public List<Bank> findBanksByBankName(String bankName);
}
