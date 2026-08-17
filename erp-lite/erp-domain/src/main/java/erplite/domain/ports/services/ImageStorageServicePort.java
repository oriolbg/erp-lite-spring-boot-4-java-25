package erplite.domain.ports.services;


import erplite.domain.entities.product.ProductImage;

public interface ImageStorageServicePort {

    ProductImage upload(String imageName, byte[] imageData);
    void delete(ProductImage img);
    byte[] download(ProductImage img);
}
