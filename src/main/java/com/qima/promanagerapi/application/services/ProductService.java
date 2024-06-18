package com.qima.promanagerapi.application.services;

import com.qima.promanagerapi.application.domain.Product;
import com.qima.promanagerapi.application.exceptions.BusinessException;
import com.qima.promanagerapi.application.ports.ProductRepositoryAdapter;
import com.qima.promanagerapi.application.ports.ProductServiceAdapter;

import java.util.List;

public class ProductService implements ProductServiceAdapter {

    private final ProductRepositoryAdapter  productRepositoryAdapter;

    public ProductService(ProductRepositoryAdapter productRepositoryAdapter) {
        this.productRepositoryAdapter = productRepositoryAdapter;
    }

    @Override
    public Product create(Product product) {
        return productRepositoryAdapter.create(product);
    }

    @Override
    public List<Product> obtainAll() {
        return productRepositoryAdapter.obtainAll();
    }

    @Override
    public Product update(Product product) {
        productRepositoryAdapter.obtainById(product.getId())
                .orElseThrow(() -> new BusinessException("Product not found"));
       return productRepositoryAdapter.create(product);
    }
}
