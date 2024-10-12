package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.exception.ProductNotFoundException;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.ProductMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = productRepository.save(productMapper.map(productRequest));
        return productMapper.map(product);
    }

    @Override
    @Transactional
    public ProductResponse findProductById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found: " + id));
        return productMapper.map(product);
    }

    @Override
    @Transactional
    public List<ProductResponse> findProducts() {
        return productRepository.findAll().stream().map(productMapper::map).toList();
    }

    @Override
    @Transactional
    public ProductResponse modifyProduct(Integer id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found: " + id));
        updateProduct(product, productRequest);
        return productMapper.map(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    private void updateProduct(Product product, ProductRequest productRequest) {
        product.setPrice(productRequest.getPrice());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
    }
}
