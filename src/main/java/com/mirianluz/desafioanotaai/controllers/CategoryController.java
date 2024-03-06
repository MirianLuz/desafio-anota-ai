package com.mirianluz.desafioanotaai.controllers;

import com.mirianluz.desafioanotaai.domain.category.Category;
import com.mirianluz.desafioanotaai.domain.category.CategoryDTO;
import com.mirianluz.desafioanotaai.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryDTO categoryData){
        Category newCategory = this.categoryService.insert(categoryData);
        return ResponseEntity.ok().body(newCategory);
    }
}
