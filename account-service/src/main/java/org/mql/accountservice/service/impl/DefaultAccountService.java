package org.mql.accountservice.service.impl;

import org.mql.accountservice.entities.Account;
import org.mql.accountservice.repository.AccountRepository;
import org.mql.accountservice.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class DefaultAccountService implements AccountService {


    private AccountRepository accountRepository;

    @Override
    public Account addAccount(Account acc) {
        return  accountRepository.save(acc);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findFirstByEmail(email);
    }
}
