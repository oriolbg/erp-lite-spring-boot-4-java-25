package erplite.domain.entities.order.events;

import erplite.domain.common.DomainEvent;
import erplite.domain.entities.order.OrderId;

import java.time.Instant;



/**
 * Emitted when order transitions SHIPPED -> DELIVERED.
 * Final state.
 *
 * @param orderId   the order identifier
 * @param timestamp the event timestamp
 */
public record OrderDelivered(
        OrderId orderId,
        Instant timestamp
) implements DomainEvent {
}
