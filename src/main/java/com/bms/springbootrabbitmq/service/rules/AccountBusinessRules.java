package com.bms.springbootrabbitmq.service.rules;

import com.bms.springbootrabbitmq.core.exceptions.BusinessException;
import com.bms.springbootrabbitmq.core.exceptions.NotFoundException;
import com.bms.springbootrabbitmq.model.Account;
import com.bms.springbootrabbitmq.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountBusinessRules {
    private final AccountRepository accountRepository;

    public AccountBusinessRules(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void checkIfAccountExistsById(final String id) {
        if (accountRepository.existsById(id)) {
            throw new BusinessException("Account already exists");
        }
    }

    public void checkIfAccountListIsEmpty(final List<Account> accounts) {
        if (accounts.isEmpty()) {
            throw new BusinessException("Account list is empty");
        }
    }

    public Account checkIfAccountExistsByAccountNumber(final Integer accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    public void checkIfAccountInsufficientBalance(final Account account, final Double amount) {
        if (account.getBalance() < amount) {
            throw new BusinessException("Insufficient balance");
        }
    }
}
