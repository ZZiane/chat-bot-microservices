package org.mql.accountservice.service;

import org.mql.accountservice.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account addAccount(Account acc);
    List<Account> getAccounts();
    Optional<Account> getAccountByEmail(String email);
    Account consume(String email);
}
