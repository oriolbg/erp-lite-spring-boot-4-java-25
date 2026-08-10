package erplite.domain.order.events;

import java.time.Instant;

import erplite.domain.common.DomainEvent;
import erplite.domain.order.OrderId;
import erplite.domain.shared.CustomerId;
import erplite.domain.shared.Money;

/**
 * Emitted when a new order is created.
 *
 * @param orderId      the order identifier
 * @param customerId   the customer identifier
 * @param customerName the customer name
 * @param totalAmount  the total order amount
 * @param timestamp    the event timestamp
 */
public record OrderCreated(
        OrderId orderId,
        CustomerId customerId,
        String customerName,
        Money totalAmount,
        Instant timestamp
) implements DomainEvent {
}
