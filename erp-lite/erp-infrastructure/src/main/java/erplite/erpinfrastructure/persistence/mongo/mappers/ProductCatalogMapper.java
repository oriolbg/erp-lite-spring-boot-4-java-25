package erplite.erpinfrastructure.persistence.mongo.mappers;

import erplite.domain.views.ProductView;
import erplite.erpinfrastructure.persistence.mongo.documents.ProductInCatalogDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductCatalogMapper {

    @Mapping(source = "currency", target = "money")
    ProductView toView(ProductInCatalogDocument document);
}
