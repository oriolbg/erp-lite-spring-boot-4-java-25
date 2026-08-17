package erplite.domain.ports.repositories;

import java.util.List;
import java.util.Optional;

import erplite.domain.entities.catalog.CatalogRoot;
import erplite.domain.entities.catalog.CatalogItem;
import erplite.domain.entities.catalog.CatalogType;

/**
* Port read-only for Catalog
 */
public interface CatalogRepositoryPort {

    Optional<CatalogRoot> findByType(CatalogType type);

    List<CatalogItem> findItemsByType(CatalogType type);

    Optional<CatalogItem> findItemByTypeAndCode(CatalogType type, String code);
}
