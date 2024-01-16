package com.zalando.ECommerceManagement.productManagement.service;

import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.productManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Product not found with ID:" + id)
        );
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    public List<Product> getProductsByIds(List<Integer> ids) {
        return productRepository.findAllById(ids);
    }
    public Product updateProduct(Product product){
       return productRepository.save(product);
    }
}
