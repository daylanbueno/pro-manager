package com.qima.promanagerapi.application.ports;

import com.qima.promanagerapi.application.domain.Category;

import java.util.List;

public interface CategoryServiceAdapter {
    Category create(Category category);

    List<Category> obtainAll();

    Category obtainById(Long id);
}
