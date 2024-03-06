package com.mirianluz.desafioanotaai.services;

import com.mirianluz.desafioanotaai.domain.category.Category;
import com.mirianluz.desafioanotaai.domain.category.CategoryDTO;
import com.mirianluz.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.mirianluz.desafioanotaai.repositories.CategoryRepository;

import java.util.List;

public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category insert(CategoryDTO categoryData){
        Category newCategory = new Category(categoryData);
        this.categoryRepository.save(newCategory);
        return newCategory;
    }

    public Category update(String id, CategoryDTO categoryData) {
        Category category = this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        if(!categoryData.title().isEmpty()) category.setTitle(categoryData.title());
        if(!categoryData.description().isEmpty()) category.setDescript(categoryData.description());
        this.categoryRepository.save(category);
        return category;
    }

    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    public void delete(String id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        this.categoryRepository.delete(category);
    }
}
