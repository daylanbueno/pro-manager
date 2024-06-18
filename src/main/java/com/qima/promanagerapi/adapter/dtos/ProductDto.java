package com.qima.promanagerapi.adapter.dtos;

import com.qima.promanagerapi.application.domain.Category;

import java.math.BigDecimal;

public record ProductDto(
         Long id,
         String name,
         BigDecimal price,
         Category category,
         String description,
         Boolean available
) {}
