package erplite.application.queries;

import erplite.application.exceptions.QueryException;
import erplite.common.enums.CatalogType;
import erplite.domain.ports.repositories.CatalogRepositoryPort;
import erplite.domain.views.ItemsView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindCatalogItemByCodeQuery {

    private final CatalogRepositoryPort catalogRepository;

    public Optional<ItemsView> execute(CatalogType type, String code) {
        log.info("Execute FindCatalogItemByCodeQuery type: {} and code: {}", type, code);

        try{
            return this.catalogRepository.findItemByTypeAndCode(type, code);
        }catch (RuntimeException e){
            log.error("Error executing FindCatalogItemByCodeQuery: ", e);
            throw new QueryException("Error executing FindCatalogItemByCodeQuery");
        }
    }
}
