package erplite.application.queries;

import erplite.application.exceptions.QueryException;
import erplite.domain.ports.repositories.ProductCatalogRepositoryPort;
import erplite.domain.views.ProductView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindProductActiveQuery {

    private final ProductCatalogRepositoryPort productCatalogRepository;

    public List<ProductView> execute() {
        log.info("Execute FindProductActiveQuery");

        try{
            return this.productCatalogRepository.findActive();
        }catch (RuntimeException e) {
            log.error("Error executing FindProductActiveQuery: ", e);
            throw new QueryException("Error executing FindProductActiveQuery");
        }
    }
}
