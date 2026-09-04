package erplite.domain.entities.product.events;

import erplite.domain.common.DomainEvent;
import erplite.domain.entities.product.ProductId;

import java.time.Instant;

/**
 * Emitted when product info is updated.
 * TRIGGERS sync to MongoDB.
 *
 * @param productId the product identifier
 * @param timestamp the event timestamp
 */
public record ProductUpdated(
        ProductId productId,
        Instant timestamp
) implements DomainEvent {
}
