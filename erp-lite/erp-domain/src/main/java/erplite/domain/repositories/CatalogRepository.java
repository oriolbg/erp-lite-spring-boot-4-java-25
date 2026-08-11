package erplite.domain.repositories;

import java.util.List;
import java.util.Optional;

import erplite.domain.catalog.Catalog;
import erplite.domain.catalog.CatalogItem;
import erplite.domain.catalog.CatalogType;

/**
* Port read-only for Catalog
 */
public interface CatalogRepository {

    Optional<Catalog> findByType(CatalogType type);

    List<CatalogItem> findItemsByType(CatalogType type);

    Optional<CatalogItem> findItemByTypeAndCode(CatalogType type, String code);
}
