package erplite.erpinfrastructure.persistence.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import erplite.erpinfrastructure.persistence.jpa.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
	
	Optional<ProductEntity> findBySku(String sku);

}
