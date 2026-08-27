package erplite.application.use_cases.order;

import erplite.application.commands.helpers.CommandHelper;
import erplite.application.commands.order.UpdateOrderStatusCommand;
import erplite.application.exceptions.CommandException;
import erplite.domain.entities.order.OrderRoot;
import erplite.domain.ports.repositories.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UpdateOrderStatusUseCase {

    private final OrderRepositoryPort orderRepository;
    private final CommandHelper commandHelper;

    public String execute(UpdateOrderStatusCommand command) {
        try {
            OrderRoot orderRoot = this.commandHelper.findOrderById(command.orderId());
            log.info("Current order current status: {}", orderRoot.getStatus());
            this.updateStatus(orderRoot, command.newStatus());
            OrderRoot orderSaved = this.orderRepository.save(orderRoot);
            log.info("Order saved current status: {}", orderSaved.getStatus());
            return orderSaved.getStatus().toString();
        } catch (IllegalStateException e) {
            log.error("Error updating order status", e);
            throw new CommandException("Error updattng order status");
        } catch (Exception e) {
            log.error("Error updating order status", e);
            throw new RuntimeException("Unexpected error updating order status");
        }
    }

    private void updateStatus(OrderRoot order, String status){
        switch(status.toUpperCase()){
            case "CONFIRMED" -> order.confirm();
            case "SHIPPED" -> order.ship();
            case "DELIERED" -> order.deliver();
            default -> throw new CommandException("Unexpected value: " + status);
        }
    }

}
