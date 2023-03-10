package com.bms.springbootrabbitmq.request;

public record CreateAccountRequest(
        String id,
        Integer accountNumber,
        Double balance
) {
}
