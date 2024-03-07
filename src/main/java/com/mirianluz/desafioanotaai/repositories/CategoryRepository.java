package com.mirianluz.desafioanotaai.repositories;

import com.mirianluz.desafioanotaai.domain.category.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
