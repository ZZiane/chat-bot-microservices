package org.mql.accountservice.repository;

import org.mql.accountservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findFirstByEmail(String email);
}
