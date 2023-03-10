package com.bms.springbootrabbitmq.service;

import com.bms.springbootrabbitmq.core.exceptions.NotFoundException;
import com.bms.springbootrabbitmq.core.utilities.services.rules.BusinessRules;
import com.bms.springbootrabbitmq.dto.AccountDto;
import com.bms.springbootrabbitmq.dto.converter.AccountDtoConverter;
import com.bms.springbootrabbitmq.model.Account;
import com.bms.springbootrabbitmq.repository.AccountRepository;
import com.bms.springbootrabbitmq.request.CreateAccountRequest;
import com.bms.springbootrabbitmq.request.TransferMoneyRequest;
import com.bms.springbootrabbitmq.request.UpdateAccountRequest;
import com.bms.springbootrabbitmq.service.rules.AccountBusinessRules;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountBusinessRules rules;
    private final AccountDtoConverter converter;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public AccountService(AccountRepository accountRepository, AccountBusinessRules rules,
                          AccountDtoConverter converter, RabbitTemplate rabbitTemplate) {
        this.accountRepository = accountRepository;
        this.rules = rules;
        this.converter = converter;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createAccount(final CreateAccountRequest request) {
        BusinessRules.run(() -> rules.checkIfAccountExistsById(request.id()));

        var account = new Account(
                request.id(),
                request.accountNumber(),
                request.balance()
        );

        accountRepository.save(account);
    }

    public void updateAccount(final String id, final UpdateAccountRequest request) {
        var account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        var updatedAccount = new Account(
                account.getId(),
                request.accountNumber(),
                request.balance()
        );

        accountRepository.save(updatedAccount);
    }

    public void deleteAccount(final String id) {
        BusinessRules.run(() -> rules.checkIfAccountExistsById(id));

        accountRepository.deleteById(id);
    }

    public void transferMoney(final TransferMoneyRequest request) {
        var account = BusinessRules.run(() -> rules.checkIfAccountExistsByAccountNumber(request.fromAccountNumber()));
        BusinessRules.run(() -> rules.checkIfAccountInsufficientBalance(account, request.amount()));

        var updatedAccount = new Account(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance() - request.amount()
        );

        accountRepository.save(updatedAccount);
        rabbitTemplate.convertAndSend(exchange, routingKey, request);
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void updateReceiverAccount(final TransferMoneyRequest request) {
        var account = BusinessRules.run(() -> rules.checkIfAccountExistsByAccountNumber(request.toAccountNumber()));

        var updatedAccount = new Account(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance() + request.amount()
        );

        accountRepository.save(updatedAccount);
    }

    public List<AccountDto> getAllAccounts() {
        var accounts = accountRepository.findAll();

        BusinessRules.run(() -> rules.checkIfAccountListIsEmpty(accounts));

        return converter.convert(accounts);
    }
}
