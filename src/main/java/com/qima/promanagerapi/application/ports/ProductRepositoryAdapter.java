package com.qima.promanagerapi.application.ports;

import com.qima.promanagerapi.application.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryAdapter {

    Product create(Product product);

    List<Product> obtainAll();

    Optional<Product> obtainById(Long id);

    void deleteById(Long id);
}
