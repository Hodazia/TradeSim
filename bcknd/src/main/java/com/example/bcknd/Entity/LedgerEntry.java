package com.example.bcknd.Entity;


import com.example.bcknd.Enums.LedgerEntryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "ledger_entries",
        indexes = {
                @Index(
                        name = "idx_ledger_wallet_created",
                        columnList = "wallet_id, created_at"
                ),
                @Index(
                        name = "idx_ledger_reference",
                        columnList = "reference_type, reference_id"
                )
        }
)
@Getter
@Setter
public class LedgerEntry extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type", nullable = false, length = 24)
    private LedgerEntryType entryType;

    @Column(name = "cash_delta", nullable = false, precision = 19, scale = 4)
    private BigDecimal cashDelta;

    @Column(
            name = "reserved_cash_delta",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal reservedCashDelta;

    @Column(
            name = "available_cash_after",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal availableCashAfter;

    @Column(
            name = "reserved_cash_after",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal reservedCashAfter;

    @Column(name = "reference_type", length = 30)
    private String referenceType;

    @Column(name = "reference_id")
    private UUID referenceId;

    @Column(length = 500)
    private String description;
}

/*

Why do we need a ledger if we already have a wallet?
The wallet shows the current balance. The ledger explains how the balance got there.

For example, a user starts with ₹100,000 and reserves ₹20,000 for an order.
Event	Cash delta	Reserved cash delta	Available after	Reserved after
Initial credit	+₹100,000	₹0	₹100,000	₹0
Reserve for order	₹0	+₹20,000	₹80,000	₹20,000
Buy execution for ₹18,000	-₹18,000	-₹18,000	₹82,000	₹2,000
Release unused reservation	₹0	-₹2,000	₹84,000	₹0

Here, cashDelta represents the change in total cash, while reservedCashDelta represents the change in reserved cash. The available balance after each operation is derived from those changes.

The settlement and reservation service must write the wallet update and corresponding ledger entries within the same database transaction.

For this design, never edit or delete an existing ledger entry to correct a balance. Use a compensating entry and fix the wallet through a controlled reconciliation operation.

For a sale, the ledger records the cash proceeds. The associated Trade records the share quantity transferred. This ledger is a cash ledger, not a complete accounting ledger for every asset



*/