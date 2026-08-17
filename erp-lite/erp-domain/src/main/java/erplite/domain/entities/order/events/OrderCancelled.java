package erplite.domain.entities.order.events;

import java.time.Instant;

import erplite.domain.common.DomainEvent;
import erplite.domain.entities.order.OrderId;

/**
 * Emitted when order is cancelled.
 * If was CONFIRMED, stock must be released.
 *
 * @param orderId   the order identifier
 * @param reason    the cancellation reason
 * @param timestamp the event timestamp
 */
public record OrderCancelled(
        OrderId orderId,
        String reason,
        Instant timestamp
) implements DomainEvent {
}
