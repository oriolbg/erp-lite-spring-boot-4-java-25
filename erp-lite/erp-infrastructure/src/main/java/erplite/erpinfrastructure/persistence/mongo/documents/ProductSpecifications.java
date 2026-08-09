package erplite.erpinfrastructure.persistence.mongo.documents;

public record ProductSpecifications(
        String processor,
        String ram,
        String storage,
        String display,
        String weight
) {}
