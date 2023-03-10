package com.bms.springbootrabbitmq.controller;

import com.bms.springbootrabbitmq.dto.AccountDto;
import com.bms.springbootrabbitmq.request.CreateAccountRequest;
import com.bms.springbootrabbitmq.request.TransferMoneyRequest;
import com.bms.springbootrabbitmq.request.UpdateAccountRequest;
import com.bms.springbootrabbitmq.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountRequest request) {
        service.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAccount(@PathVariable String id, @RequestBody UpdateAccountRequest request) {
        service.updateAccount(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        service.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferMoneyRequest request) {
        service.transferMoney(request);
        return ResponseEntity.ok("Money transferred successfully");
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(service.getAllAccounts());
    }
}
