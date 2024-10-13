package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.impl;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.ProductNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.ProductMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.ProductRepository;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private static final String PRODUCT_NOT_FOUND = "Product not found: %s.";

    @Override
    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = productRepository.save(productMapper.map(productRequest));
        log.info("Product added: {}", product.getId());
        return productMapper.map(product);
    }

    @Override
    @Transactional
    public ProductResponse findProductById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
        {
            log.error("Product not found: {}", id);
            return new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND, id));
        });
        log.info("Product found: {}", product.getId());
        return productMapper.map(product);
    }

    @Override
    @Transactional
    public List<ProductResponse> findProducts() {
        List<ProductResponse> productResponses = productRepository.findAll().stream().map(productMapper::map).toList();
        log.info("Retrieved {} products", productResponses.size());
        return productResponses;
    }

    @Override
    @Transactional
    public ProductResponse modifyProduct(Integer id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() ->
        {
            log.error("Product not found for modification: {}", id);
            return new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND, id));
        });
        updateProduct(product, productRequest);
        log.info("Product modified: {}", product.getId());
        return productMapper.map(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
        log.info("Product deleted: {}", id);
    }

    private void updateProduct(Product product, ProductRequest productRequest) {
        product.setPrice(productRequest.getPrice());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
    }
}
