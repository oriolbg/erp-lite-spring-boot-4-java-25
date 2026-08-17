package erplite.application.commands.helpers;


import erplite.application.exceptions.CommandException;
import erplite.domain.entities.order.OrderId;
import erplite.domain.entities.order.OrderRoot;
import erplite.domain.ports.repositories.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommandHelper {

    private final OrderRepositoryPort orderRepository;

    public OrderRoot findOrderById(String orderId) {
        log.info("Finding order by ID: {}", orderId);

        return this.orderRepository.findById(OrderId.of(UUID.fromString(orderId)))
                .orElseThrow(() -> new CommandException("Order not found"));
    }
}
