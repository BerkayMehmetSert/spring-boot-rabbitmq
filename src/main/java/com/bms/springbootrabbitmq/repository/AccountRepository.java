package com.bms.springbootrabbitmq.repository;

import com.bms.springbootrabbitmq.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByAccountNumber(int accountNumber);
}