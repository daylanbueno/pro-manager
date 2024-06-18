package com.qima.promanagerapi.adapter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_category")
public class CategoryEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Transient
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryEntity categoryParent;

    public CategoryEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryEntity(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}
