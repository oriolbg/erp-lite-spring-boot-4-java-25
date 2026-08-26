package erplite.erpapi.controllers.queries;


import erplite.application.exceptions.QueryException;
import erplite.application.queries.FindCatalogByTypeQuery;
import erplite.application.queries.FindCatalogItemByCodeQuery;
import erplite.application.queries.FindCatalogItemsByTypeQuery;
import erplite.common.enums.CatalogType;
import erplite.domain.views.CatalogView;
import erplite.domain.views.ItemsView;
import erplite.erpapi.dtos.BaseResponseWrapper;
import erplite.erpapi.paths.ApiPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = ApiPaths.QUERIES_CATALOGS, version = "1")
@RequiredArgsConstructor
public class QueryCatalogControllerV1 {

    private final FindCatalogByTypeQuery findCatalogByTypeQuery;
    private final FindCatalogItemsByTypeQuery findCatalogItemsByTypeQuery;
    private final FindCatalogItemByCodeQuery findCatalogItemByCodeQuery;


    @GetMapping(path = "/{type}")
    public ResponseEntity<BaseResponseWrapper<CatalogView>> getByType(@PathVariable String type) {
        log.info("GET catalog by type: {}", type);

        CatalogType catalogType = CatalogType.valueOf(type.toUpperCase());

        CatalogView response = this.findCatalogByTypeQuery.execute(catalogType)
                .orElseThrow(() -> new QueryException("Catalog with type " + type + " not found"));

        return ResponseEntity.ok(BaseResponseWrapper.of(response));
    }

    @GetMapping(path = "/{type}/items")
    public ResponseEntity<BaseResponseWrapper<List<ItemsView>>> getItemsByType(@PathVariable String type) {
        log.info("GET catalog items by type: {}", type);

        CatalogType catalogType = CatalogType.valueOf(type.toUpperCase());

        List<ItemsView> response = this.findCatalogItemsByTypeQuery.execute(catalogType);

        return ResponseEntity.ok(BaseResponseWrapper.of(response));
    }

    @GetMapping(path = "/{type}/items", params = "code")
    public ResponseEntity<BaseResponseWrapper<ItemsView>> getItemByTypeAndCode(
            @PathVariable String type,
            @RequestParam String code) {
        log.info("GET catalog item by type: {} and code: {}", type, code);

        CatalogType catalogType = CatalogType.valueOf(type.toUpperCase());

        ItemsView response = this.findCatalogItemByCodeQuery.execute(catalogType, code)
                .orElseThrow(() -> new QueryException("Item with code " + code + " not found for type " + type));

        return ResponseEntity.ok(BaseResponseWrapper.of(response));
    }
}
