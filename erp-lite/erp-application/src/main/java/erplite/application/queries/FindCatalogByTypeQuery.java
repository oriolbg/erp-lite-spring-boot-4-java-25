package erplite.application.queries;

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
        return this.catalogRepository.findByType(catalogType);
    }
}
