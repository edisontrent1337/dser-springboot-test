package de.dser.bespringboottest;

import de.dser.bespringboottest.api.repositories.BankRepository;
import de.dser.bespringboottest.entities.Bank;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest(classes =  {BeSpringbootTestApplication.class})
@RunWith(SpringRunner.class)
public class SpringBootJPAIntegrationTest {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void testPersistingOfBank() {
        Bank bank = new Bank();
        bank.setBankName("Bankname");
        bank.setBanknumber(456789);

        Bank savedBank = bankRepository.save(bank);

        Assert.assertEquals(bank, savedBank);

    }

}
