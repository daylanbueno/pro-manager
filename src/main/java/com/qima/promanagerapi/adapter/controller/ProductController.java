package com.qima.promanagerapi.adapter.controller;

import com.qima.promanagerapi.adapter.converters.ProductConverter;
import com.qima.promanagerapi.adapter.dtos.ProductDto;
import com.qima.promanagerapi.application.domain.Product;
import com.qima.promanagerapi.application.ports.ProductServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceAdapter productServiceAdapter;
    private final ProductConverter productConverter;

    @PostMapping
    private ProductDto create(@RequestBody ProductDto productDto){
        Product product = productServiceAdapter.create(productConverter.toDomain(productDto));
        return productConverter.toDto(product);
    }

    @PutMapping("/{id}")
    private ProductDto update(@RequestBody ProductDto productDto, @PathVariable Long id){
        productDto = productDto.withId(id);
        Product product = productServiceAdapter.update(productConverter.toDomain(productDto));
        return productConverter.toDto(product);
    }


    @GetMapping
    private List<ProductDto> obtainAll(){
        return productServiceAdapter
                .obtainAll()
                .stream().map(productConverter::toDto).toList();
    }

}
