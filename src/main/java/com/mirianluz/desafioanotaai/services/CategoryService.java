package com.mirianluz.desafioanotaai.services;

import com.mirianluz.desafioanotaai.domain.category.Category;
import com.mirianluz.desafioanotaai.domain.category.CategoryDTO;
import com.mirianluz.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.mirianluz.desafioanotaai.repositories.CategoryRepository;
import com.mirianluz.desafioanotaai.services.aws.AwsSnsService;
import com.mirianluz.desafioanotaai.services.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final AwsSnsService snsService;

    public CategoryService(CategoryRepository categoryRepository, AwsSnsService snsService){
        this.categoryRepository = categoryRepository;
        this.snsService = snsService;
    }

    public Category insert(CategoryDTO categoryData){
        Category newCategory = new Category(categoryData);
        this.categoryRepository.save(newCategory);
        this.snsService.publish(new MessageDTO(newCategory.toString()));
        return newCategory;
    }

    public Category update(String id, CategoryDTO categoryData) {
        Category category = this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        if(!categoryData.title().isEmpty()) category.setTitle(categoryData.title());
        if(!categoryData.description().isEmpty()) category.setDescript(categoryData.description());
        this.categoryRepository.save(category);
        this.snsService.publish(new MessageDTO(category.toString()));
        return category;
    }

    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }
    public Optional<Category> getById(String id) {
        return this.categoryRepository.findById(id);
    }

    public void delete(String id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        this.categoryRepository.delete(category);
    }
}
