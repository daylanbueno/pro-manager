package com.qima.promanagerapi.application.services;

import com.qima.promanagerapi.application.domain.Category;
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
        return categoryRepositoryAdapter.create(category);
    }

    @Override
    public List<Category> obtainAll() {
        return categoryRepositoryAdapter.obtainAll();
    }
}
