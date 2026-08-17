package erplite.domain.ports.repositories;


import erplite.domain.entities.order.OrderId;
import erplite.domain.entities.order.OrderRoot;
import erplite.domain.entities.order.OrderNumber;
import erplite.domain.shared.CustomerId;

import java.util.List;
import java.util.Optional;

/**
 * Port for storage or consult orders
 */
public interface OrderRepositoryPort {

    OrderRoot save(OrderRoot order);
    Optional<OrderRoot> findById(OrderId id);
    Optional<OrderRoot> findAllByOrderNumber(OrderNumber orderNumber);
    List<OrderRoot> findByCustomerId(CustomerId customerId);
    void delete(OrderRoot order);
}
