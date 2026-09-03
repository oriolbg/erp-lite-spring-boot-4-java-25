package erplite.erpinfrastructure.persistence.mongo.adapters;


import erplite.domain.ports.repositories.ProductCatalogRepositoryPort;
import erplite.domain.views.ProductView;
import erplite.erpinfrastructure.persistence.mongo.mappers.ProductCatalogMapper;
import erplite.erpinfrastructure.persistence.mongo.repositories.ProductInCatalogRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static erplite.common.constants.CacheConstants.*;

@Repository
@Slf4j
@AllArgsConstructor
public class ProductCatalogRepositoryAdapter implements ProductCatalogRepositoryPort {

    private final ProductInCatalogRepository productInCatalogRepository;
    private final ProductCatalogMapper productCatalogMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<ProductView> findById(String id) {
        log.info("Find product by id: {}", id);

        Object raw = redisTemplate.opsForValue().get(CACHE_PRODUCTS_BY_ID + id);
        if(raw!=null){
            log.debug("Found product by id in cache: {}", id);
            return Optional.of(this.objectMapper.convertValue(raw, ProductView.class));
        }

        log.debug("Finding product by id in mongo: {}", id);
        return this.productInCatalogRepository.findById(id).map(productCatalogMapper::toView);
    }

    @Override
    public Optional<ProductView> findBySku(String sku) {
        log.info("Find product by sku: {}", sku);

        Object raw = redisTemplate.opsForValue().get(CACHE_PRODUCTS_BY_SKU + sku);
        if(raw!=null){
            log.debug("Found product by sku in cache: {}", sku);
            return Optional.of(this.objectMapper.convertValue(raw, ProductView.class));
        }

        log.debug("Finding product by sku in mongo: {}", sku);
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

         Object raw = this.redisTemplate.opsForList().range(CACHE_PRODUCTS_BY_CATEGORY + category, 0, -1);
         if(raw!=null){
             log.debug("Found product by category in cache: {}", category);
             return this.objectMapper.convertValue(raw,
                    this.objectMapper.getTypeFactory().constructCollectionType(List.class, ProductView.class)
             );
         }

         log.debug("Finding product by category in mongo: {}", category);
         return this.productInCatalogRepository.findByCategoryIdAndActiveTrue(category)
                 .stream().map(productCatalogMapper::toView).toList();
    }

    @Override
    public List<ProductView> findActive() {
        log.info("Find active products");

        Object raw = this.redisTemplate.opsForList().range(CACHE_PRODUCTS_ACTIVE, 0, -1);
        if(raw!=null){
            log.debug("Found active products in cache");
            return this.objectMapper.convertValue(raw,
                    this.objectMapper.getTypeFactory().constructCollectionType(List.class, ProductView.class)
            );
        }

        log.info("Finding active products");
        return this.productInCatalogRepository.findByActiveTrueOrderByIdAsc()
                .stream().map(productCatalogMapper::toView).toList();
    }
}
