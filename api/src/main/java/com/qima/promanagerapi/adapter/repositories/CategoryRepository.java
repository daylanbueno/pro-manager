package com.qima.promanagerapi.adapter.repositories;

import com.qima.promanagerapi.adapter.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
