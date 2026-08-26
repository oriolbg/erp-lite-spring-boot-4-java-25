package erplite.erpinfrastructure.persistence.mongo.adapters;


import erplite.domain.ports.repositories.ProductCatalogRepositoryPort;
import erplite.domain.views.CatalogView;
import erplite.domain.views.ProductView;
import erplite.erpinfrastructure.persistence.mongo.mappers.ProductCatalogMapper;
import erplite.erpinfrastructure.persistence.mongo.repositories.ProductInCatalogRepository;
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
public class ProductCatalogRepositoryAdapter implements ProductCatalogRepositoryPort {

    private final ProductInCatalogRepository productInCatalogRepository;
    private final ProductCatalogMapper productCatalogMapper;
    private final CacheManager cacheManager;

    @Override
    public Optional<ProductView> findById(String id) {
        log.info("Find product by id: {}", id);

        Cache cache = this.cacheManager.getCache(CACHE_PRODUCTS_BY_ID);
        if(cache!=null){
            ProductView productInCache = cache.get(id, ProductView.class);
            if(productInCache != null){
                log.info("Find product by id in cache: {}", productInCache);
                return Optional.of(productInCache);
            }
        }

        return this.productInCatalogRepository.findById(id).map(productCatalogMapper::toView);
    }

    @Override
    public Optional<ProductView> findBySku(String sku) {
        log.info("Find product by sku: {}", sku);

        Cache cache = this.cacheManager.getCache(CACHE_PRODUCTS_BY_SKU);
        if(cache!=null){
            ProductView productInCache = cache.get(sku, ProductView.class);
            if(productInCache != null){
                log.info("Find product by sku in cache: {}", productInCache);
                return Optional.of(productInCache);
            }
        }

        return this.productInCatalogRepository.findBySku(sku).map(productCatalogMapper::toView);
    }

    @Override
    public List<ProductView> findByText(String text) {
        log.info("Find product by text: {}", text);
        return this.productInCatalogRepository.findByTextAndActive(text)
                .stream().map(productCatalogMapper::toView).toList();
    }

    @Override
    public List<ProductView> findByCategory(String category) {
        log.info("Find product by category: {}", category);

        Cache cache = this.cacheManager.getCache(CACHE_PRODUCTS_BY_CATEGORY);
        if(cache!=null){
            List<ProductView> productsInCache = cache.get("all", List.class);
            if(productsInCache != null){
                log.info("Find products by category in cache: {}", productsInCache);
                return productsInCache;
            }
        }

        return this.productInCatalogRepository.findByCategoryIdAndActiveTrue(category)
                .stream().map(productCatalogMapper::toView).toList();
    }

    @Override
    public List<ProductView> findActive() {
        log.info("Find active products");
        return this.productInCatalogRepository.findByActiveTrueOrderByIdAsc()
                .stream().map(productCatalogMapper::toView).toList();
    }
}
