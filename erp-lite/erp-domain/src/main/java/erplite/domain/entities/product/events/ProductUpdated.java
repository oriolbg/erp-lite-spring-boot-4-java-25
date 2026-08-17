package erplite.domain.entities.catalog.product.events;

import java.time.Instant;

import erplite.domain.common.DomainEvent;
import erplite.domain.entities.product.ProductId;

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
