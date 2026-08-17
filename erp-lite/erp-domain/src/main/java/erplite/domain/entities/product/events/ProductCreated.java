package erplite.domain.entities.catalog.product.events;

import java.time.Instant;

import erplite.domain.common.DomainEvent;
import erplite.domain.entities.product.ProductId;
import erplite.domain.entities.product.ProductName;
import erplite.domain.entities.product.SKU;
import erplite.domain.shared.Money;

/**
 * Emitted when a new product is created.
 * TRIGGERS sync to MongoDB (CQRS).
 *
 * @param productId the product identifier
 * @param sku       the product SKU
 * @param name      the product name
 * @param price     the product price
 * @param timestamp the event timestamp
 */
public record ProductCreated(
        ProductId productId,
        SKU sku,
        ProductName name,
        Money price,
        Instant timestamp
) implements DomainEvent {
}
