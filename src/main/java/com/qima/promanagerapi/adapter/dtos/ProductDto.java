package com.qima.promanagerapi.adapter.dtos;

import lombok.With;

import java.math.BigDecimal;

public record ProductDto(
         @With
         Long id,
         String name,
         BigDecimal price,
         Long categoryId,
         String description,
         Boolean available
) {}
