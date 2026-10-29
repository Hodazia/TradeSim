package com.example.bcknd.Entity;


import com.example.bcknd.Enums.OrderSide;
import com.example.bcknd.Enums.OrderStatus;
import com.example.bcknd.Enums.OrderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "trade_orders",
        indexes = {
                @Index(
                        name = "idx_orders_stock_status_created",
                        columnList = "stock_id, status, created_at"
                ),
                @Index(
                        name = "idx_orders_user_created",
                        columnList = "user_id, created_at"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class TradeOrder extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private OrderSide side;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private OrderStatus status = OrderStatus.OPEN;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "filled_quantity", nullable = false)
    private long filledQuantity = 0;

    @Column(name = "limit_price", precision = 19, scale = 4)
    private BigDecimal limitPrice;

    @Column(
            name = "reserved_cash_amount",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal reservedCashAmount = BigDecimal.ZERO;
}

/*


What do the fields mean?
Consider a limit buy order:
Stock: NOVA
Side: BUY
Type: LIMIT
Quantity: 100
Limit price: ₹100
Filled quantity: 40
Status: PARTIALLY_FILLED

The user wants to buy 100 shares at ₹100 or less. They have purchased 40 so far, so 60 shares
remain.

The unfilled quantity is:
\[
Q_{\text{remaining}} =
Q_{\text{total}} - Q_{\text{filled}}
\]

\[
100 - 40 = 60
\]

The reservedCashAmount tracks how much cash remains reserved for that order. Your settlement service must adjust it as fills happen and release any remainder when the order is cancelled or completed.

For a limit order, limitPrice is required. For a market order, it is null; the backend
 instead follows its market-order execution and spending-cap rules.

The matching engine uses the side, stock, type, price, creation time, status, and
remaining quantity to decide which orders can match.

Important: A TradeOrder is not a trade. An order can remain open, be partially filled,
 be completely filled, or be cancelled. A single order can generate multiple Trade records.

Your service must validate quantities and prices, enforce legal state transitions, and
 prevent overfilling. Those rules belong in the domain/service layer and database constraints
 where appropriate—not merely in frontend validation.

 */