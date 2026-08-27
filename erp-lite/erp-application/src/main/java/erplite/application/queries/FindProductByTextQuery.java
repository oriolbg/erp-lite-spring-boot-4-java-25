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
public class FindProductByTextQuery {

    private final ProductCatalogRepositoryPort productCatalogRepository;

    public List<ProductView> execute(String text) {
        log.info("Execute FindProductByTextQuery text: {}", text);

        try{
            return this.productCatalogRepository.findByText(text);
        }catch (RuntimeException e) {
            log.error("Error executing FindProductByTextQuery: ", e);
            throw new QueryException("Error executing FindProductByTextQuery");
        }
    }
}
