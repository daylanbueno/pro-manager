package com.qima.promanagerapi.adapter.repositories;

import com.qima.promanagerapi.adapter.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
