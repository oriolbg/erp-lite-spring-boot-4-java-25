package erplite.erpinfrastructure.persistence.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import erplite.erpinfrastructure.persistence.mongo.documents.ProductInCatalogDocument;

public interface ProductInCatalogRepository extends MongoRepository<ProductInCatalogDocument, String>{

}
