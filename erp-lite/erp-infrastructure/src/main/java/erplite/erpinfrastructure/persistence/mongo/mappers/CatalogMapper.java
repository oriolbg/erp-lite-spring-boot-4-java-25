package erplite.erpinfrastructure.persistence.mongo.mappers;


import erplite.domain.views.CatalogView;
import erplite.domain.views.ItemsView;
import erplite.erpinfrastructure.persistence.mongo.documents.CatalogDocument;
import erplite.erpinfrastructure.persistence.mongo.documents.CatalogItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CatalogMapper {

    @Mapping(source = "catalogType", target = "type")
    CatalogView toView(CatalogDocument document);

    ItemsView toItemView(CatalogItem item);
}
