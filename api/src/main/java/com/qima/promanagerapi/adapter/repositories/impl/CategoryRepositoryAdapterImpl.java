package com.qima.promanagerapi.adapter.repositories.impl;

import com.qima.promanagerapi.adapter.converters.CategoryConverter;
import com.qima.promanagerapi.adapter.entities.CategoryEntity;
import com.qima.promanagerapi.adapter.repositories.CategoryRepository;
import com.qima.promanagerapi.application.domain.Category;
import com.qima.promanagerapi.application.ports.CategoryRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapterImpl implements CategoryRepositoryAdapter {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public Category create(Category category) {
        CategoryEntity entity = categoryConverter.toEntity(category);
        if (category.getCategoryParent() != null) {
            entity.setCategoryParent(categoryConverter.toEntity(category.getCategoryParent()));
        }
        CategoryEntity categoryEntity = categoryRepository.save(entity);
        return categoryConverter.toDomain(categoryEntity);
    }

    @Override
    public List<Category> obtainAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryConverter::toDomain).toList();
    }

    @Override
    public Optional<Category> obtainById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryConverter::toDomain);
    }
}
