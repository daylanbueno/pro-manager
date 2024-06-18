package com.qima.promanagerapi.adapter.dtos;

public record CategoryDto(
        Long id,
        String name,
        Long categoryId
) {
    public CategoryDto(Long id, String name) {
        this(id, name, null);
    }
}
