package com.mirianluz.desafioanotaai.domain.product;

import com.mirianluz.desafioanotaai.domain.category.CategoryDTO;

public record ProductDTO(String title, String description, String ownerId, Integer price, String categoryId) {
}
