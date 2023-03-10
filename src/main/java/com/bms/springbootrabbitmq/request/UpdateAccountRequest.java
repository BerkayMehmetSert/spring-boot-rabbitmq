package com.bms.springbootrabbitmq.request;

public record UpdateAccountRequest(
        Integer accountNumber,
        Double balance
) {
}
