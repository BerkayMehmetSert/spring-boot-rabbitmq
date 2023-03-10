package com.bms.springbootrabbitmq.dto.converter;

import com.bms.springbootrabbitmq.core.dto.DtoConverter;
import com.bms.springbootrabbitmq.dto.AccountDto;
import com.bms.springbootrabbitmq.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoConverter implements DtoConverter<AccountDto, Account> {
    @Override
    public AccountDto convert(Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance()
        );
    }

}
