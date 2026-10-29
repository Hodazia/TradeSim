package com.example.bcknd.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stocks",
        uniqueConstraints = @UniqueConstraint(
                name="uk_stocks_symbol",
                columnNames = "symbol"
        )
)
@Getter
@Setter
@NoArgsConstructor
public class Stock extends BaseEntity {

    @Column(nullable = false)
    private String symbol; // u can have an s3 bucket to store the stock symbol & fetch it in react page

    @Column(nullable = false, length = 150)
    private String name; // stock name

    @Column(length = 60)
    private String sector; // sector like tech,

    @Column(length = 1000)
    private String description; // brief description about it

    @Column(nullable = false)
    private boolean active = true; // by default obviously all the stocks will be active only
}

/*

we will be inserting 20-50 stocks, we will seed it in the DB, manually,
what data will we store, and the stock price keeps changing, so
today and tomorrow the difference will be there

What about the inital price of the stock?? that we will seed in the DB?



 */
