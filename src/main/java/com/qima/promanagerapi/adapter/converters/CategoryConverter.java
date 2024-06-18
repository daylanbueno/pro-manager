package com.qima.promanagerapi.adapter.converters;

import com.qima.promanagerapi.adapter.dtos.CategoryDto;
import com.qima.promanagerapi.adapter.entities.CategoryEntity;
import com.qima.promanagerapi.application.domain.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public  Category toDomain(CategoryDto categoryDto) {
        return new Category(
                categoryDto.id(),
                categoryDto.name()
        );
    }

    public CategoryEntity toEntity(Category category) {
        return new CategoryEntity(
                category.getId(),
                category.getName()
        );
    }

    public Category toDomain(CategoryEntity categoryEntity) {
        return new Category(
                categoryEntity.getId(),
                categoryEntity.getName()
        );
    }
}
