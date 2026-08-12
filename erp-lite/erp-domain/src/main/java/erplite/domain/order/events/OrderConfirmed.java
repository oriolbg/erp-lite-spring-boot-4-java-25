package erplite.domain.order.events;

import java.time.Instant;

import erplite.domain.common.DomainEvent;
import erplite.domain.order.OrderId;

/**
 * Emitted when order transitions PENDING -> CONFIRMED.
 * TRIGGERS stock decrement.
 *
 * @param orderId   the order identifier
 * @param timestamp the event timestamp
 */
public record OrderConfirmed(
        OrderId orderId,
        Instant timestamp
) implements DomainEvent {
}
