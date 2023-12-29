package org.mql.accountservice.web;

import org.mql.accountservice.dto.AuthRequestDTO;
import org.mql.accountservice.dto.TokenResponseDTO;
import org.mql.accountservice.entities.Account;
import org.mql.accountservice.service.AccountService;
import org.mql.accountservice.service.impl.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountRestController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private AccountService accountService;
    public AccountRestController(AccountService accountService,
                                 AuthenticationManager authenticationManager,
                                 JwtService jwtService
                                 ) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<Account> usersList(){
        return accountService.getAccounts();
    }

    @GetMapping("/accounts/actualuser")
    public ResponseEntity<Account> getUser(Principal principal){
        Optional<Account> optional = accountService.getAccountByEmail(principal.getName());
        if(optional.isPresent()){
            return ResponseEntity.ok(optional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping( "/accounts/solde/consume")
    public Account consumeSolde(Principal principal){
        return accountService.consume(principal.getName());
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/accounts/addNewUser")
    public Account addNewUser(@RequestBody Account account) {
        return accountService.addAccount(account);
    }



    @PostMapping("/generateToken")
    public ResponseEntity<TokenResponseDTO> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if(authentication.isAuthenticated()){
                return ResponseEntity.ok(TokenResponseDTO.builder()
                        .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername())).build());
            } else {
                return ResponseEntity.notFound().build();
            }
    }

}
