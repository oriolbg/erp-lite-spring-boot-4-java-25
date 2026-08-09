package erplite.erpinfrastructure.persistence.jpa.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import erplite.erpinfrastructure.persistence.jpa.entities.OrderProductEntity;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID>{

}
