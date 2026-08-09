package erplite.erpinfrastructure.persistence.mongo.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import erplite.erpinfrastructure.persistence.mongo.documents.AuditLogDocument;

public interface AuditLogRepository extends MongoRepository<AuditLogDocument, ObjectId>{

}
