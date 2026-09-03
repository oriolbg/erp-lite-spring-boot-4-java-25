package erplite.erpinfrastructure.persistence.redis;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static erplite.common.constants.CacheConstants.*;

/**
 * Se genera esta Clase para configurar estaticamente la propiedad de duracion del TTL
 * En el caso de requerir más propiedades, se deberia hacer con un archivo YAML
 */
@Configuration
@EnableCaching
public class RedisConfig {

    private static final Duration REDIS_CACHE_TTL = Duration.ofHours(24);

    /**
     * Habilita las anotaciones equivalentes de Spring
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        GenericJacksonJsonRedisSerializer serializer = GenericJacksonJsonRedisSerializer.builder()
                //No es necesario usar restricciones de tipo en los serializadores
                //El cache se usara en 2 microservicios distintos por lo que no se puede ser tan restrictivo --> se usara RedisTemplate
//            .typePropertyName("_type")
//            .enableUnsafeDefaultTyping()
            .build();

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(REDIS_CACHE_TTL)
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

        //Estructura de configuracion de claves donde aplicará el caché
        Map<String, RedisCacheConfiguration> configsMap = new HashMap<>();
        configsMap.put(CACHE_PRODUCTS_BY_ID, configuration);
        configsMap.put(CACHE_PRODUCTS_BY_SKU, configuration);
        configsMap.put(CACHE_PRODUCTS_BY_CATEGORY, configuration);
        configsMap.put(CACHE_PRODUCTS_ACTIVE, configuration);
        configsMap.put(CACHE_CATALOGS_BY_TYPE, configuration);
        configsMap.put(CACHE_CATALOGS_ITEMS, configuration);

        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(configsMap)
                .build();
    }

    /**
     * Permite habilitar el patrón Template (inyeccón Autowired)
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        GenericJacksonJsonRedisSerializer serializer = GenericJacksonJsonRedisSerializer.builder()
                //No es necesario usar restricciones de tipo en los serializadores
                //El cache se usara en 2 microservicios distintos por lo que no se puede ser tan restrictivo --> se usara RedisTemplate
//            .typePropertyName("_type")
//            .enableUnsafeDefaultTyping()
            .build();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // cache manager
        redisTemplate.setValueSerializer(serializer); // cache manager
        redisTemplate.setHashKeySerializer(new StringRedisSerializer()); // redis template
        redisTemplate.setHashValueSerializer(serializer);  // redis template

        return redisTemplate;
    }
}
