package com.example.bcknd.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "trades",
        indexes = {
                @Index(
                        name = "idx_trades_stock_executed",
                        columnList = "stock_id, executed_at"
                ),
                @Index(
                        name = "idx_trades_buy_order",
                        columnList = "buy_order_id"
                ),
                @Index(
                        name = "idx_trades_sell_order",
                        columnList = "sell_order_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Trade extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buy_order_id", nullable = false)
    private TradeOrder buyOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sell_order_id", nullable = false)
    private TradeOrder sellOrder;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Column(nullable = false)
    private long quantity;

    @Column(name = "executed_at", nullable = false)
    private Instant executedAt;
}

/*

Example
A buyer wants 100 NOVA shares at a limit price of ₹105. Two sell orders are available:
- 40 shares at ₹100.
- 60 shares at ₹102.
If your price-time matching rules allow these matches, the engine records two executions:
Trade	Price	Quantity	Value
Trade 1	₹100	40	₹4,000
Trade 2	₹102	60	₹6,120
Total	—	100	₹10,120

The buyer's total cost is ₹10,120, before any fees (which V1 can omit). The execution price is the price agreed by the matching engine—typically the resting order's limit price in the design we discussed.

These trade records are used to:
- Update both users' holdings and wallets.
- Update the latest market state.
- Update the corresponding candlestick.
- Display the trade history.

*/