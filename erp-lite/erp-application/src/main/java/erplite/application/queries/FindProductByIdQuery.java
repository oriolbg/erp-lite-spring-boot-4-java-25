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
public class FindProductByIdQuery {

    private final ProductCatalogRepositoryPort productCatalogRepository;

    public Optional<ProductView> execute(String id) {
        log.info("Execute FindProductByIdQuery id: {}", id);

        try{
            return this.productCatalogRepository.findById(id);
        }catch (RuntimeException e) {
            log.error("Error executing FindProductByIdQuery: ", e);
            throw new QueryException("Error executing FindProductByIdQuery");
        }
    }
}
