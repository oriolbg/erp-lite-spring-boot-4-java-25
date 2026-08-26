package erplite.erpinfrastructure.persistence.mongo.adapters;

import erplite.common.enums.CatalogType;
import erplite.domain.ports.repositories.CatalogRepositoryPort;
import erplite.domain.views.CatalogView;
import erplite.domain.views.ItemsView;
import erplite.erpinfrastructure.persistence.mongo.mappers.CatalogMapper;
import erplite.erpinfrastructure.persistence.mongo.repositories.CatalogRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static erplite.common.constants.CacheConstants.*;

@Repository
@Slf4j
@AllArgsConstructor
public class CatalogRepositoryAdapter implements CatalogRepositoryPort {

    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;
    private final CacheManager cacheManager;


    @Override
    public Optional<CatalogView> findByType(CatalogType type) {
        log.info("Find catalog by type: {}", type);

        Cache cache = this.cacheManager.getCache(CACHE_CATALOGS_BY_TYPE);
        if(cache!=null){
            CatalogView catalogInCache = cache.get(type.name(), CatalogView.class);
            if (catalogInCache != null) {
                log.info("Found catalog in cache: {}", catalogInCache);
                return Optional.of(catalogInCache);
            }
        }

        return this.catalogRepository.findByCatalogType(type).map(catalogMapper::toView);
    }

    @Override
    public List<ItemsView> findItemsByType(CatalogType type) {
        log.info("Find items catalog by type: {}", type);

        Cache cache = this.cacheManager.getCache(CACHE_CATALOGS_ITEMS);
        if (cache != null) {
            List<ItemsView> itemsInCache = cache.get(type.name(), List.class);
            if (itemsInCache != null) {
                log.info("Found catalog items in cache, total: {}", itemsInCache.size());
                return itemsInCache;
            }
        }

        return this.catalogRepository.findByCatalogType(type)
                .map(doc -> doc.getItems()
                        .stream()
                        .map(catalogMapper::toItemView)
                        .toList()
                )
                .orElse(List.of());
    }

    @Override
    public Optional<ItemsView> findItemByTypeAndCode(CatalogType type, String code) {
        log.info("Find items catalog by type: {} and code: {}", type, code);
        return this.catalogRepository.findByCatalogType(type)
                .flatMap(doc -> doc.getItems()//Aplanar el flujo cuando trabajamos varios Optional/Listas
                    .stream()
                    .filter(item ->item.code().equals(code))
                    .findFirst()
                    .map(catalogMapper::toItemView));
    }
}
