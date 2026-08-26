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
public class FindProductByCategoryQuery {

    private final ProductCatalogRepositoryPort productCatalogRepository;

    public List<ProductView> execute(String category) {
        log.info("Execute FindProductByCategoryQuery category: {}", category);

        try{
            return this.productCatalogRepository.findByCategory(category);
        }catch (RuntimeException e) {
            log.error("Error executing FindProductByCategoryQuery: ", e);
            throw new QueryException("Error executing FindProductByCategoryQuery");
        }
    }
}
