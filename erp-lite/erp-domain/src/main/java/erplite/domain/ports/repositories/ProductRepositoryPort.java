package erplite.domain.ports.repositories;


import erplite.domain.entities.order.OrderRoot;
import erplite.domain.entities.product.ProductId;
import erplite.domain.entities.product.ProductRoot;

import java.util.Optional;

/**
 * Port for storage or consult products
 */
public interface ProductRepositoryPort {

    ProductRoot save(ProductRoot product);
    Optional<ProductRoot> findAllById(ProductId id);
    Optional<ProductRoot> findBySku(String sku);
    void delete(ProductRoot product);
}
