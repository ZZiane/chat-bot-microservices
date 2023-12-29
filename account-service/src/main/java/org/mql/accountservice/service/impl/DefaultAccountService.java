package org.mql.accountservice.service.impl;
import org.mql.accountservice.entities.Account;
import org.mql.accountservice.repository.AccountRepository;
import org.mql.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class DefaultAccountService implements AccountService {


    @Autowired
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

    public Account consume(String email) {
        Optional<Account> optionalAcc = accountRepository.findFirstByEmail(email);
        if(optionalAcc.isPresent()){
            Account acc = optionalAcc.get();
            double accountSolde = acc.getSolde();
            if(accountSolde >= 1){
                acc.setSolde(accountSolde-1);
                accountRepository.save(acc); 
            }
            return acc;
        }
        throw new RuntimeException("Error account undefined");
    }




}
