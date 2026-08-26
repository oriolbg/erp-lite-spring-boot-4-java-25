package erplite.erpapi.controllers.commands;

import erplite.application.commands.product.CreateProductCommand;
import erplite.application.commands.product.DeactivateProductCommand;
import erplite.application.commands.product.UpdateProductCommand;
import erplite.application.commands.product.UpdateStockCommand;
import erplite.application.use_cases.product.CreateProductUseCase;
import erplite.application.use_cases.product.DeactivateProductUseCase;
import erplite.application.use_cases.product.UpdateProductUseCase;
import erplite.application.use_cases.product.UpdateStockUseCase;
import erplite.erpapi.paths.ApiPaths;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping(ApiPaths.COMMANDS_PRODUCTS)
@RequiredArgsConstructor
public class CommandProductControllerV1 {

    private final CreateProductUseCase createProductUseCase;
    private final DeactivateProductUseCase deactivateProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final UpdateStockUseCase updateStockUseCase;

    @PostMapping
    public ResponseEntity<Void> postProduct(@Valid @RequestPart(value = "product") CreateProductCommand productCommandReq,
                                            @RequestPart(value = "image") MultipartFile img) throws IOException {
        log.info("POST product");

        var command = new CreateProductCommand(
                productCommandReq.sku(),
                productCommandReq.name(),
                productCommandReq.description(),
                productCommandReq.price(),
                productCommandReq.currency(),
                productCommandReq.stock(),
                productCommandReq.categoryId(),
                img != null ? img.getBytes() : null,
                img != null ? img.getOriginalFilename() : null,
                productCommandReq.createdBy()
        );

        String productId = this.createProductUseCase.execute(command);

        return ResponseEntity.created(URI.create(ApiPaths.COMMANDS_PRODUCTS + "/" + productId)).build();
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<Void> putProduct(@PathVariable String id,
                                           @Valid @RequestPart(value = "product") UpdateProductCommand productCommandReq,
                                           @RequestPart(value = "image") MultipartFile img) throws IOException {
        log.info("PUT product");

        var command = new UpdateProductCommand(
                id,
                productCommandReq.name(),
                productCommandReq.description(),
                productCommandReq.price(),
                productCommandReq.categoryId(),
                img != null ? img.getBytes() : null,
                img != null ? img.getOriginalFilename() : null
        );

        this.updateProductUseCase.execute(command);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path="/{id}/deactivate")
    public ResponseEntity<Void> patchProductDeactivate(@PathVariable String id) {
        log.info("PATCH product deactivate");

        var command = new DeactivateProductCommand(id);

        this.deactivateProductUseCase.execute(command);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path="/{id}/stock")
    public ResponseEntity<Void> patchProductStock(@PathVariable String id,
                                                  @Valid @RequestBody UpdateStockCommand stockCommand) {
        log.info("PATCH product stock");

        var command = new UpdateStockCommand(
                id,
                stockCommand.quantity(),
                stockCommand.reason()
        );

        this.updateStockUseCase.execute(command);

        return ResponseEntity.noContent().build();
    }
}
