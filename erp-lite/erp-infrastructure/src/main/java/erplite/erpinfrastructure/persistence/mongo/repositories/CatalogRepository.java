package erplite.erpinfrastructure.persistence.mongo.repositories;

import erplite.common.enums.CatalogType;
import org.springframework.data.mongodb.repository.MongoRepository;

import erplite.erpinfrastructure.persistence.mongo.documents.CatalogDocument;

import java.util.Optional;

public interface CatalogRepository extends MongoRepository<CatalogDocument, String>{

    Optional<CatalogDocument> findByCatalogType(CatalogType type);
}
