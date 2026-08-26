package erplite.application.queries;

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
        return this.catalogRepository.findItemByTypeAndCode(type, code);
    }
}
