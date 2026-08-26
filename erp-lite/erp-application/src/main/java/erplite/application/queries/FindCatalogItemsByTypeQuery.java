package erplite.application.queries;

import erplite.common.enums.CatalogType;
import erplite.domain.ports.repositories.CatalogRepositoryPort;
import erplite.domain.views.ItemsView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindCatalogItemsByTypeQuery {

    private final CatalogRepositoryPort catalogRepository;

    public List<ItemsView> execute(CatalogType catalogType) {
        log.info("Execute FindCatalogItemsByTypeQuery");
        return this.catalogRepository.findItemsByType(catalogType);
    }
}
