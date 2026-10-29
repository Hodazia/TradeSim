package com.example.bcknd.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "market_states",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_market_state_stock",
                columnNames = "stock_id"
        )
)
@Getter
@Setter
@NoArgsConstructor
public class MarketState extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(
            name = "reference_price",
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal referencePrice;

    @Column(
            name = "last_traded_price",
            precision = 19,
            scale = 4
    )
    private BigDecimal lastTradedPrice;

    @Column(name = "last_trade_at")
    private Instant lastTradeAt;
}

/*

what is the reference price,

so i guess the market state will keep updating after every stock buy/sell option,
it will update to the recent tradedprice,
and also the instant when it was lasttraded at

So usually the stock market opens at 9 or 10 , at that there is no history of candles, it
starts at 9 or 10, so the previous day OLHC remains for the today's opening so
what shall we store in the candles for day from 9-10,
because we will make a live trade on a given time, yeah not on the live market stock, but
on the random data that we will generate,

Candles
suppose at 9:15, OLHC will all be same,
at 9:20 AM, 4 trades were executed,
one more thing how does OLHC updates at the start of the day,when they all are equal

*/