package com.qima.promanagerapi.adapter.converters;

import com.qima.promanagerapi.adapter.dtos.CategoryDto;
import com.qima.promanagerapi.adapter.entities.CategoryEntity;
import com.qima.promanagerapi.application.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryConverter {

    public CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getParentId()
        );
    }

    public  Category toDomain(CategoryDto categoryDto) {
        return new Category(
                categoryDto.id(),
                categoryDto.name(),
                categoryDto.categoryId()
        );
    }

    public CategoryEntity toEntity(Category category) {
        return new CategoryEntity(
                category.getId(),
                category.getName(),
                category.getCategoryParent() != null ? category.getCategoryParent().getParentId() : null
        );
    }

    public Category toDomain(CategoryEntity categoryEntity) {
        return new Category(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getCategoryParent() != null ? categoryEntity.getCategoryParent().getId() : null
        );
    }
}
