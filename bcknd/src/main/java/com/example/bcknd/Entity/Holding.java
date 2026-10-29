package com.example.bcknd.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;


@Entity
@Table(
        name = "holdings",
        uniqueConstraints = @UniqueConstraint(
                name="uk_holding_user_stock",
                columnNames = {"user_id","stock_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
public class Holding extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "available_quantity", nullable = false)
    private long availableQuantity = 0; // available quantity for all the stocks,

    @Column(name = "reserved_quantity", nullable = false)
    private long reservedQuantity = 0; // reserved quantity for all or a particular stock
}

/*


Field	Quantity
Available	100
Reserved	0

After reserving the 60 shares:
Field	Quantity
Available	40
Reserved	60

The user cannot submit another sell order for the same 60 shares because they are already reserved.

If 20 shares are sold, the service deducts 20 from the reserved quantity and reduces the total
ownership accordingly. Any remaining reserved shares stay reserved until they execute or the
order is cancelled.

The uniqueness constraint means each user gets one holding record per stock. If they don't own
any shares yet, your service can create the holding when needed.


*/
