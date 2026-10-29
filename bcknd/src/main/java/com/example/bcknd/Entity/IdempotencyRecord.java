package com.example.bcknd.Entity;
import com.example.bcknd.Enums.IdempotencyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "idempotency_records",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_idempotency_user_key",
                columnNames = {"user_id", "idempotency_key"}
        )
)
@Getter
@Setter
@NoArgsConstructor
public class IdempotencyRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "idempotency_key", nullable = false, length = 100)
    private String idempotencyKey;

    @Column(name = "request_hash", nullable = false, length = 64)
    private String requestHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private IdempotencyStatus status = IdempotencyStatus.PROCESSING;

    @Column(name = "resource_id")
    private UUID resourceId;

    @Column(name = "response_status")
    private Integer responseStatus;

    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;
}

/*

imagine a user clicks buy , the server process the order, but the network times out before the
response reaches the browser, the browser retries the request,
without idempotency, the user could accidentally place the same order twice ,
this table helps your API recognize that a repeated request is the same logical operation.

The client sends an idempotency key with an order request:
POST /api/v1/orders
Idempotency-Key: 98d0f1c2-...


The backend:
1. Checks whether that user already has a record for the key.
2. If not, creates a record associated with the request hash.
3. Processes the order and stores its resulting response or order ID.
4. If the same key and same request are submitted again, returns the previous result instead of placing another order.
5. If the same key is reused for a different request body, rejects it.

One implementation detail: if you persist PROCESSING and the order in the same transaction, other
 transactions generally won't see that uncommitted record. A duplicate can instead block on the
  unique constraint and then retrieve the completed result. Handle that flow deliberately.
  If you want independently committed in-progress records, you need a more elaborate recovery
  strategy for abandoned requests.

For the MVP, the simplest robust design is to coordinate idempotency with the order transaction,
use a unique constraint, and return the stored result after a duplicate request is resolved.

*/