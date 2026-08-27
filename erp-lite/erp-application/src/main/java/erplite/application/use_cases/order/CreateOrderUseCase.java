package erplite.application.use_cases.order;

import erplite.application.commands.order.CreateOrderCommand;
import erplite.application.exceptions.CommandException;
import erplite.domain.entities.order.Customer;
import erplite.domain.entities.order.OrderItem;
import erplite.domain.entities.order.OrderNumber;
import erplite.domain.entities.order.OrderRoot;
import erplite.domain.entities.product.ProductId;
import erplite.domain.entities.product.ProductRoot;
import erplite.domain.ports.repositories.OrderRepositoryPort;
import erplite.domain.ports.repositories.ProductRepositoryPort;
import erplite.domain.ports.services.CustomerProviderServicePort;
import erplite.domain.ports.services.OrderConfirmEmailServicePort;
import erplite.domain.shared.CustomerId;
import erplite.domain.shared.Email;
import erplite.domain.shared.Quantity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * JIRA TICKET: ERP-6734
 * Business Rules:
 * - Customer must exist in external system (JSONPlaceholder)
 * - All products must exist and be active
 * - All products must have sufficient stock
 * - Order number is generated automatically
 * Flow:
 * 1. Validate customer exists
 * 2. Validate products exist
 * 3. Create order items from products (snapshot prices)
 * 4. Create order aggregate (domain generates ID)
 * 5. Persist order (write model - PostgreSQL)
 * 6. Publish domain events (for MongoDB sync, email notifications)
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderRepositoryPort orderRepository;
    private final ProductRepositoryPort productRepository;
    private final CustomerProviderServicePort customerProviderService;
    private final OrderConfirmEmailServicePort emailService;

    public String execute(CreateOrderCommand command) {
        log.info("Creating order {}", command);
        try {
            Customer customer = this.validateAndGet(command.customerId());

            List<OrderItem> items = this.createOrderItems(command.items());

            OrderNumber orderNumber = this.generateOrderNumber();

            OrderRoot orderRoot =   OrderRoot.create(
                    orderNumber,
                    customer,
                    items,
                    command.createdBy()
            );

            OrderRoot savedOrder = this.orderRepository.save(orderRoot);

            log.info("Saved order with id {}", savedOrder.getId());

            this.sendMail(orderRoot, customer);

            return orderRoot.getId().value().toString();
        } catch (IllegalArgumentException iae) {
            log.error("Invalid data", iae);
            throw new CommandException("Error on create order msg: " + iae.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error", e);
            throw new CommandException(e.getMessage());
        }
    }

    private Customer validateAndGet(Long customerId) {
        log.info("Validating customer id {}", customerId);
        var customerInfo = this.customerProviderService.findById(customerId)
                .orElseThrow(() -> new CommandException("Customer not found is " + customerId));

        log.info("Customer validated with name {}", customerInfo.name());

        return Customer.of(
                CustomerId.of(customerId),
                customerInfo.name()
        );
    }

    private List<OrderItem> createOrderItems(List<CreateOrderCommand.OrderItemRequest>commandItems) {
        log.info("Creating order items");

        return commandItems.stream()
                .map(this::toOrderItem)
                .toList();
    }

    private OrderItem toOrderItem(CreateOrderCommand.OrderItemRequest commandItem) {
        ProductRoot productRoot = this.productRepository
                .findAllById(ProductId.of(UUID.fromString(commandItem.productId())))
                .orElseThrow(() -> new CommandException("Product not found"));

        Quantity quantity = Quantity.of(commandItem.quantity());

        return OrderItem.from(productRoot, quantity);
    }

    private OrderNumber generateOrderNumber(){
        int sequence = (int) (System.currentTimeMillis() % 1000);
        return OrderNumber.generate(sequence);
    }

    private void publishDomainEvent(OrderRoot order){
        var events = order.getDomainEvents();
        log.info("Publishing domain events: {}", events);
        events.forEach(event -> {
            log.debug("Try to publishing event: {}", event);
            //TODO: enviar evento
        });

        order.clearDomainEvents();
        log.info("Events published SUCCESSFULLY");
    }

    private void sendMail(OrderRoot order, Customer customer) {
        try {
            log.info("Sending mail: {}", customer.customerName() + "@gmail.com");
            final var mail = Email.of("debuggeandoideas@gmail.com");

            this.emailService.sendMail(
                    mail,
                    order.getId(),
                    order.getOrderNumber().value(),
                    order.getTotalAmount(),
                    customer.customerName(),
                    order.getItems().size()
            );
        } catch (Exception e) {
            log.error("Error sending mail", e);
            throw new CommandException("Error sending mail: " + e.getMessage());
        }
    }
}
