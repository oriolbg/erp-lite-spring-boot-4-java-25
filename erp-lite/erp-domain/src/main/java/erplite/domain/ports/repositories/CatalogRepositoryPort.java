package erplite.domain.ports.repositories;

import erplite.common.enums.CatalogType;
import erplite.domain.views.CatalogView;
import erplite.domain.views.ItemsView;

import java.util.List;
import java.util.Optional;


/**
* Port read-only for Catalog
 */
public interface CatalogRepositoryPort {

    Optional<CatalogView> findByType(CatalogType type);

    List<ItemsView> findItemsByType(CatalogType type);

    Optional<ItemsView> findItemByTypeAndCode(CatalogType type, String code);
}
