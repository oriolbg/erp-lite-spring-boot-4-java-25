package erplite.erpinfrastructure.persistence.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import erplite.erpinfrastructure.persistence.mongo.documents.CatalogDocument;

public interface CatalogRepository extends MongoRepository<CatalogDocument, String>{

}
