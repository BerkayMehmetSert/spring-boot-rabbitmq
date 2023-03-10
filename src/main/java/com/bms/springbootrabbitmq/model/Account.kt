package com.bms.springbootrabbitmq.model

import com.bms.springbootrabbitmq.core.model.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Account @JvmOverloads constructor(
    @Id
    val id: String,
    val accountNumber: Int,
    val balance: Double
): BaseEntity {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false
        if (accountNumber != other.accountNumber) return false
        if (balance != other.balance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + accountNumber
        result = 31 * result + balance.hashCode()
        return result
    }

    override fun toString(): String {
        return "Account(id='$id', accountNumber=$accountNumber, balance=$balance)"
    }
}
