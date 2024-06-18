package com.qima.promanagerapi.application.ports;

import com.qima.promanagerapi.application.domain.Category;

import java.util.List;

public interface CategoryRepositoryAdapter {
    Category create(Category category);

    List<Category> obtainAll();
}
