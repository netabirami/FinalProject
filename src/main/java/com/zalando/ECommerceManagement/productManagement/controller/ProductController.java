package com.zalando.ECommerceManagement.productManagement.controller;

import com.zalando.ECommerceManagement.productManagement.exception.ProductNotFoundException;
import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.productManagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProduct() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping("/byIds")
    public List<Product> getProductsByIds(@RequestParam List<Integer> ids) {
        return productService.getProductsByIds(ids);
    }

    @PostMapping
    public Product addProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id, @Valid @RequestBody Product updateProduct) {
        Product existingProduct = productService.getProductById(id);
        existingProduct.setName(updateProduct.getName());
        existingProduct.setDescription(updateProduct.getDescription());
        existingProduct.setPrice(updateProduct.getPrice());
        existingProduct.setStock(updateProduct.getStock());
        return productService.updateProduct(existingProduct);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct (@PathVariable Integer id){
        productService.deleteProduct(id);
    }
}
