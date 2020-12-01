package de.dser.bespringboottest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan("de.dser.bespringboottest.entities")
@EnableJpaRepositories("de.dser.bespringboottest.api.repositories")
@EnableTransactionManagement
@EnableJpaAuditing
public class BeSpringbootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeSpringbootTestApplication.class, args);
    }

}
