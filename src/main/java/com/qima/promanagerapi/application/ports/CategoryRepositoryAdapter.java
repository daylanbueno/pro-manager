package com.qima.promanagerapi.application.ports;

import com.qima.promanagerapi.application.domain.Category;

public interface CategoryRepositoryAdapter {
    Category create(Category category);
}
