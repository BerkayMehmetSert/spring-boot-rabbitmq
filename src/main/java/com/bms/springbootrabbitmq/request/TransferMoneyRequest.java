package com.bms.springbootrabbitmq.request;

public record TransferMoneyRequest (
        Integer fromAccountNumber,
        Integer toAccountNumber,
        Double amount
){
}
