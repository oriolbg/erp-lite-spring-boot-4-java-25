package erplite.application.queries;

import erplite.domain.ports.repositories.ProductCatalogRepositoryPort;
import erplite.domain.views.ProductView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindProductBySkuQuery {

    private final ProductCatalogRepositoryPort productCatalogRepository;

    public Optional<ProductView> execute(String sku) {
        log.info("Execute FindProductBySkuQuery sku: {}", sku);
        return this.productCatalogRepository.findBySku(sku);
    }
}
