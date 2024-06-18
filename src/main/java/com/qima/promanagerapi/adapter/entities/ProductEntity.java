package com.qima.promanagerapi.adapter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity(name = "tb_product")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    private String description;
    private Boolean available;
}
