package com.qima.promanagerapi.adapter.repositories.impl;

import com.qima.promanagerapi.adapter.converters.ProductConverter;
import com.qima.promanagerapi.adapter.entities.ProductEntity;
import com.qima.promanagerapi.adapter.repositories.ProductRepository;
import com.qima.promanagerapi.application.domain.Product;
import com.qima.promanagerapi.application.ports.ProductRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapterImpl implements ProductRepositoryAdapter {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Override
    public Product create(Product product) {
        ProductEntity productEntity = productRepository.save(productConverter.toEntity(product));
        return productConverter.toDomain(productEntity);
    }

    @Override
    public List<Product> obtainAll() {
        return productRepository
                .findAll()
                .stream()
                .map(productConverter::toDomain).toList();

    }

    @Override
    public Optional<Product> obtainById(Long id) {
        return productRepository.findById(id)
                .map(productConverter::toDomain);
    }
}
