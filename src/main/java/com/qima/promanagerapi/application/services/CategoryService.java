package com.qima.promanagerapi.application.services;

import com.qima.promanagerapi.application.domain.Category;
import com.qima.promanagerapi.application.exceptions.BusinessException;
import com.qima.promanagerapi.application.ports.CategoryRepositoryAdapter;
import com.qima.promanagerapi.application.ports.CategoryServiceAdapter;

import java.util.List;

public class CategoryService implements CategoryServiceAdapter {

    private final CategoryRepositoryAdapter categoryRepositoryAdapter;

    public CategoryService(CategoryRepositoryAdapter categoryRepositoryAdapter) {
        this.categoryRepositoryAdapter = categoryRepositoryAdapter;
    }

    @Override
    public Category create(Category category) {
        if (category.getParentId() != null) {
            Category categoryParent = categoryRepositoryAdapter.obtainById(category.getParentId())
                    .orElseThrow(() -> new BusinessException("Parent category not found"));
            category.setCategoryParent(categoryParent);
        }
        return categoryRepositoryAdapter.create(category);
    }

    @Override
    public List<Category> obtainAll() {
        return categoryRepositoryAdapter.obtainAll();
    }

    @Override
    public Category obtainById(Long id) {
        return categoryRepositoryAdapter.obtainById(id)
                .orElseThrow(() -> new BusinessException("Category not found"));
    }
}
