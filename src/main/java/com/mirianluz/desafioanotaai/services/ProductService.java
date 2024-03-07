package com.mirianluz.desafioanotaai.services;

import com.mirianluz.desafioanotaai.domain.category.Category;
import com.mirianluz.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.mirianluz.desafioanotaai.domain.product.Product;
import com.mirianluz.desafioanotaai.domain.product.ProductDTO;
import com.mirianluz.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.mirianluz.desafioanotaai.repositories.ProductRepository;
import com.mirianluz.desafioanotaai.services.aws.AwsSnsService;
import com.mirianluz.desafioanotaai.services.aws.MessageDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    private final AwsSnsService snsService;

    public ProductService(CategoryService categoryService, ProductRepository productRepository, AwsSnsService snsService){
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.snsService = snsService;
    }

    public Product insert(ProductDTO productData){
        Category category = this.categoryService.getById(productData.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);
        newProduct.setCategory(category);
        this.productRepository.save(newProduct);
        this.snsService.publish(new MessageDTO(newProduct.getOwnerId()));
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
        this.snsService.publish(new MessageDTO(product.getOwnerId()));
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
