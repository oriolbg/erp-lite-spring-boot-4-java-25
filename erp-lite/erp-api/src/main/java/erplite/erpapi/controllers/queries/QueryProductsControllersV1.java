package erplite.erpapi.controllers.queries;

import erplite.application.queries.*;
import erplite.domain.views.ProductView;
import erplite.erpapi.dtos.BaseResponseWrapper;
import erplite.erpapi.paths.ApiPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(ApiPaths.QUERIES_PRODUCTS)
@RequiredArgsConstructor
public class QueryProductsControllersV1 {

    private final FindProductByCategoryQuery findProductByCategoryQuery;
    private final FindProductByIdQuery findProductByIdQuery;
    private final FindProductActiveQuery findProductActiveQuery;
    private final FindProductByTextQuery findProductByTextQuery;
    private final FindProductBySkuQuery findProductBySkuQuery;

    @GetMapping(path = "/{id}")
    public ResponseEntity<BaseResponseWrapper<ProductView>> getById(@PathVariable String id){
        log.info("GET product by id: {}", id);

        ProductView response = findProductByIdQuery.execute(id)
                .orElseThrow(() -> new QueryException("Product with id " + id + " not found"));

        return ResponseEntity.ok(BaseResponseWrapper.of(response));
    }

    @GetMapping(params = "sku")
    public ResponseEntity<BaseResponseWrapper<ProductView>> getBySku(@RequestParam String sku){
        log.info("GET product by sku: {}", sku);

        ProductView response = findProductBySkuQuery.execute(sku)
                .orElseThrow(() -> new QueryException("Product with sku " + sku + " not found"));

        return ResponseEntity.ok(BaseResponseWrapper.of(response));
    }

    @GetMapping(path = "/active")
    public ResponseEntity<BaseResponseWrapper<List<ProductView>>> getByActive(){
        log.info("GET product active");

       List<ProductView> response = findProductActiveQuery.execute();
       if(response.isEmpty()){
           log.info("No active products found");
           return ResponseEntity.noContent().build();
       }

       return ResponseEntity.ok(BaseResponseWrapper.of(response));
    }

    @GetMapping(path = "/search", params = "text")
    public ResponseEntity<BaseResponseWrapper<List<ProductView>>> getByText(@RequestParam String text){
        log.info("GET product by text: {}", text);

        List<ProductView> response = findProductByTextQuery.execute(text);
        if(response.isEmpty()){
            log.info("No product by text found");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(BaseResponseWrapper.of(response));
    }

    @GetMapping(params = "category")
    public ResponseEntity<BaseResponseWrapper<List<ProductView>>> getByCategory(@RequestParam String category){
        log.info("GET product by category: {}", category);

        List<ProductView> response = findProductByCategoryQuery.execute(category);
        if(response.isEmpty()){
            log.info("No product by category found");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(BaseResponseWrapper.of(response));
    }

}
