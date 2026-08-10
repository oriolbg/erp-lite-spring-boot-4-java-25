package erplite.domain.ports;

import erplite.domain.product.ProductImage;

public interface ImageStorageService {

    ProductImage upload(String imageName, byte[] imageData);
    void delete(ProductImage img);
    byte[] download(ProductImage img);
}
