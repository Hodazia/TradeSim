package com.example.bcknd.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "candles",
uniqueConstraints = @UniqueConstraint(
        name = "uk_candle_stock_interval_time",
        columnNames = {
                "stock_id", "interval", "bucket_start"
        }),
        indexes = @Index(
                name = "idx_candle_stock_interval_time",
                columnList = "stock_id, interval, bucket_start"
        )
)
@Getter
@Setter
@NoArgsConstructor
public class Candle extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "interval", nullable = false, length = 8)
    private String interval; // it can be "1m" or "5m" set it as "5m" for now

    @Column(name = "bucket_start", nullable = false)
    private Instant bucketStart; // it is the start of the candles minute, not the time when the row was inserted

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal open;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal high;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal low;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal close;

    @Column(nullable = false)
    private long volume;
}

/*

a stock can have many candles, but each candle belongs to one stock.
this is a @ManytoOne relationship
The composite uniqueness constraint ensures that the same stock cannot have
duplicate one-minute candles for the same timestamp.





*/