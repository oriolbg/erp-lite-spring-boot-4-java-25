package erplite.erpapi.controllers.commands;

import erplite.application.commands.order.CancelOrderCommand;
import erplite.application.commands.order.CreateOrderCommand;
import erplite.application.commands.order.UpdateOrderStatusCommand;
import erplite.application.use_cases.order.CancelOrderUseCase;
import erplite.application.use_cases.order.CreateOrderUseCase;
import erplite.application.use_cases.order.UpdateOrderStatusUseCase;
import erplite.erpapi.paths.ApiPaths;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(ApiPaths.COMMANDS_ORDERS)
@RequiredArgsConstructor
public class CommandOrderControllerV1 {

    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    @PostMapping
    public ResponseEntity<Void> postOrder(@Valid @RequestBody CreateOrderCommand createOrderCommand){
        log.info("POST order");

        String productId = this.createOrderUseCase.execute(createOrderCommand);

        return ResponseEntity.created(URI.create(ApiPaths.COMMANDS_ORDERS + "/" + productId )).build();

    }

    @PatchMapping(path = "/{id}/cancel")
    public ResponseEntity<Void> patchOrderCancel(@PathVariable String id, @RequestParam String reason){
        log.info("PATCH cancel order");

        var command = new CancelOrderCommand(id, reason);

        this.cancelOrderUseCase.execute(command);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}/status")
    public ResponseEntity<Void> patchOrderStatus(@PathVariable String id, @RequestParam String status){
        log.info("PATCH order status");

        var command = new UpdateOrderStatusCommand(id, status);

        this.updateOrderStatusUseCase.execute(command);

        return ResponseEntity.noContent().build();
    }
}
