package erplite.application.queries;

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
        return this.productCatalogRepository.findActive();
    }
}
