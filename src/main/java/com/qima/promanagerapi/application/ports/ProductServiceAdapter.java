package com.qima.promanagerapi.application.ports;

import com.qima.promanagerapi.application.domain.Product;

import java.util.List;

public interface ProductServiceAdapter {
    Product create(Product product);
    List<Product> obtainAll();
    Product update(Product domain);
}
