package erplite.domain.product.events;

import java.time.Instant;

import erplite.domain.common.DomainEvent;
import erplite.domain.product.ProductId;

/**
 * Emitted when product is deactivated.
 * TRIGGERS sync to MongoDB.
 *
 * @param productId the product identifier
 * @param timestamp the event timestamp
 */
public record ProductDeactivated(
        ProductId productId,
        Instant timestamp
) implements DomainEvent {
}
