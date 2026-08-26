package erplite.application.queries;

import erplite.application.exceptions.QueryException;
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

        try{
            return this.productCatalogRepository.findBySku(sku);
        }catch (RuntimeException e) {
            log.error("Error executing FindProductBySkuQuery: ", e);
            throw new QueryException("Error executing FindProductBySkuQuery");
        }
    }
}
