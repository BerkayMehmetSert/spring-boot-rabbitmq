package com.bms.springbootrabbitmq.dto

import com.bms.springbootrabbitmq.core.dto.Dto

data class AccountDto(
    val id: String,
    val accountNumber: Int,
    val balance: Double
) : Dto
