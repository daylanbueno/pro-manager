package com.qima.promanagerapi.application.domain;

public class Category {

    private Long id;
    private String name;
    private Category categoryParent;
    private Long parentId;

    public Category() {}

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategoryParent() {
        return categoryParent;
    }

    public void setCategoryParent(Category categoryParent) {
        this.categoryParent = categoryParent;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
