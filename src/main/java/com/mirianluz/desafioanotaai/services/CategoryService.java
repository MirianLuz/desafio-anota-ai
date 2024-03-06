package com.mirianluz.desafioanotaai.services;

import com.mirianluz.desafioanotaai.domain.category.Category;
import com.mirianluz.desafioanotaai.domain.category.CategoryDTO;
import com.mirianluz.desafioanotaai.repositories.CategoryRepository;

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
}
