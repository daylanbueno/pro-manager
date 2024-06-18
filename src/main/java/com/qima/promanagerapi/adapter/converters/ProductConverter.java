package com.qima.promanagerapi.adapter.converters;

import com.qima.promanagerapi.adapter.dtos.ProductDto;
import com.qima.promanagerapi.adapter.entities.ProductEntity;
import com.qima.promanagerapi.application.domain.Product;
import com.qima.promanagerapi.application.ports.CategoryServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final CategoryConverter categoryConverter;
    private final CategoryServiceAdapter categoryServiceAdapter;

    public Product toDomain(ProductDto productDto) {
        return new Product(
                productDto.id(),
                productDto.name(),
                productDto.price(),
                categoryServiceAdapter.obtainById(productDto.categoryId()),
                productDto.description(),
                productDto.available()
        );
    }

    public  ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getDescription(),
                product.getAvailable()
        );
    }

    public ProductEntity toEntity(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getPrice(),
                categoryConverter.toEntity(product.getCategory()),
                product.getDescription(),
                product.getAvailable()
        );
    }

    public Product toDomain(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                categoryConverter.toDomain(productEntity.getCategory()),
                productEntity.getDescription(),
                productEntity.getAvailable()
        );
    }
}
