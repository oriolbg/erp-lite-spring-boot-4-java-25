package erplite.application.queries;

import erplite.application.exceptions.QueryException;
import erplite.common.enums.CatalogType;
import erplite.domain.ports.repositories.CatalogRepositoryPort;
import erplite.domain.views.CatalogView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindCatalogByTypeQuery {

    private final CatalogRepositoryPort catalogRepository;

    public Optional<CatalogView> execute(CatalogType catalogType) {
        log.info("Execute FindCatalogByTypeQuery");

        try{
            return this.catalogRepository.findByType(catalogType);
        }catch (RuntimeException e){
            log.error("Error executing FindCatalogByTypeQuery: ", e);
            throw new QueryException("Error executing FindCatalogByTypeQuery");
        }
    }
}
