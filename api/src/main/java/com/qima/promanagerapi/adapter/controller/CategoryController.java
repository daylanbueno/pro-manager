package com.qima.promanagerapi.adapter.controller;

import com.qima.promanagerapi.adapter.converters.CategoryConverter;
import com.qima.promanagerapi.adapter.dtos.CategoryDto;
import com.qima.promanagerapi.application.domain.Category;
import com.qima.promanagerapi.application.ports.CategoryServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceAdapter categoryServiceAdapter;
    private final CategoryConverter categoryConverter;

    @PostMapping
    private CategoryDto create(@RequestBody CategoryDto categoryDto){
        Category category = categoryServiceAdapter.create(categoryConverter.toDomain(categoryDto));
        return categoryConverter.toDto(category);
    }

    @GetMapping
    private List<CategoryDto>  obtainAll(){
        List<Category> categories = categoryServiceAdapter.obtainAll();
        return categories.stream().map(categoryConverter::toDto).toList() ;
    }

}
