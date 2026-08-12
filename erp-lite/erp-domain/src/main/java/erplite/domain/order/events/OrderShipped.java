package erplite.domain.order.events;

import java.time.Instant;

import erplite.domain.common.DomainEvent;
import erplite.domain.order.OrderId;

/**
 * Emitted when order transitions CONFIRMED -> SHIPPED.
 *
 * @param orderId   the order identifier
 * @param timestamp the event timestamp
 */
public record OrderShipped(
        OrderId orderId,
        Instant timestamp
) implements DomainEvent {
}
