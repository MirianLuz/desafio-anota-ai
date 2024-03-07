package com.mirianluz.desafioanotaai.services;

import com.mirianluz.desafioanotaai.domain.category.Category;
import com.mirianluz.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.mirianluz.desafioanotaai.domain.product.Product;
import com.mirianluz.desafioanotaai.domain.product.ProductDTO;
import com.mirianluz.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.mirianluz.desafioanotaai.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private CategoryService categoryService;
    private ProductRepository productRepository;

    public ProductService(CategoryService categoryService, ProductRepository productRepository){
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }

    public Product insert(ProductDTO productData){
        Category category = this.categoryService.getById(productData.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);
        newProduct.setCategory(category);
        this.productRepository.save(newProduct);
        return newProduct;
    }

    public Product update(String id, ProductDTO productData) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        if(productData.categoryId() != null){
            this.categoryService.getById(productData.categoryId()).ifPresent(product::setCategory);
        }

        if(!productData.title().isEmpty()) product.setTitle(productData.title());
        if(!productData.description().isEmpty()) product.setDescription(productData.description());
        if(!(productData.price() == null)) product.setPrice(productData.price());
        this.productRepository.save(product);
        return product;
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    public void delete(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        this.productRepository.delete(product);
    }
}
