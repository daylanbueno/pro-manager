package com.qima.promanagerapi.adapter.repositories.impl;

import com.qima.promanagerapi.adapter.converters.CategoryConverter;
import com.qima.promanagerapi.adapter.entities.CategoryEntity;
import com.qima.promanagerapi.adapter.repositories.CategoryRepository;
import com.qima.promanagerapi.application.domain.Category;
import com.qima.promanagerapi.application.ports.CategoryRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapterImpl implements CategoryRepositoryAdapter {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public Category create(Category category) {
        CategoryEntity categoryEntity = categoryRepository.save(categoryConverter.toEntity(category));
        return categoryConverter.toDomain(categoryEntity);
    }
}
