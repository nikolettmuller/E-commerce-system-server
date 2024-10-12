package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.service;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.ProductResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper.ProductMapper;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
