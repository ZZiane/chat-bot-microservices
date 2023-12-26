package org.mql.accountservice.web;

import org.mql.accountservice.entities.Account;
import org.mql.accountservice.entities.AuthRequest;
import org.mql.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {

    private AccountService accountService;
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/accounts")
    public List<Account> usersList(){
        return accountService.getAccounts();
    }


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/accounts/{email}")
    public ResponseEntity<Account> getUserByEmail(@PathVariable String email){
        return ResponseEntity.of(accountService.getAccountByEmail(email));
    }



    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public Account addNewUser(@RequestBody Account account) {
        return accountService.addAccount(account);
    }


    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return "XXXXX";
    }

}
