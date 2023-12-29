package org.mql.accountservice;

import org.mql.accountservice.entities.Account;
import org.mql.accountservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AccountRepository accountRepository, PasswordEncoder encoder){
        return args -> {

            List<Account> customerList= List.of(
                    Account.builder()
                            .firstName("root")
                            .lastName("root")
                            .email("root")
                            .password(encoder.encode("root"))
                            .solde(4500d)
                            .build(),
                    Account.builder()
                            .firstName("admin")
                            .lastName("admin")
                            .email("admin")
                            .password(encoder.encode("admin"))
                            .solde(2d)
                            .build()

            );
            accountRepository.saveAll(customerList);
        };
    }
}
