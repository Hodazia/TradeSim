package com.example.bcknd.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet",
        uniqueConstraints = @UniqueConstraint(
                name="uk_wallets_user",
                columnNames = "user_id"
        )
        // the above defines unique constraints on multiple column, currently only on user_id
)
@Getter
@Setter
@NoArgsConstructor
public class Wallet extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private AppUser user;

    @Column(
            name = "available_cash",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal availableCash = BigDecimal.ZERO;

    @Column(
            name = "reserved_cash",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal reservedCash = BigDecimal.ZERO;
}


/*
imagine a user starts with 100,000, they place a limit buy order requiring 20,000 of reserved cash
LIMIT BUY ORDER?
Field	Before order	After reservation
Available cash	₹100,000	₹80,000
Reserved cash	₹0	₹20,000
Total cash	₹100,000	₹100,000


The reservation does not mean the user has spent the money. It means the money cannot be used for another order while it is reserved.

If the order executes for ₹18,000, the settlement service deducts the actual cost and releases any unused reservation. If the order is cancelled, the remaining reservation is released.

Invariant: Total cash} =Available cash} + Reserved cash}



*
*
*
*
*
* */