package com.mirianluz.desafioanotaai.domain.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "category")
public class Category {
    @Id
    private String id;
    private String title;
    private String descript;
    private String ownerId;

    public Category(CategoryDTO categoryDTO) {
        this.title = categoryDTO.title();
        this.descript = categoryDTO.description();
        this.ownerId = categoryDTO.ownerId();
    }
}
