package org.mql.accountservice;

import org.mql.accountservice.entities.Account;
import org.mql.accountservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AccountRepository accountRepository){
        return args -> {

            List<Account> customerList= List.of(
                    Account.builder()
                            .firstName("root")
                            .lastName("root")
                            .email("root")
                            .build(),
                    Account.builder()
                            .firstName("admin")
                            .lastName("admin")
                            .email("admin")
                            .build()

            );
            accountRepository.saveAll(customerList);
        };
    }
}
