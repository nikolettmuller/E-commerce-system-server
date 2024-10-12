package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(ProductRequest productRequest);

    ProductResponse findProductById(Integer id);

    List<ProductResponse> findProducts();

    ProductResponse modifyProduct(Integer id, ProductRequest productRequest);

    void deleteProduct(Integer id);
}
