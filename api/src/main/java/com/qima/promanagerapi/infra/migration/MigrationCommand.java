package com.qima.promanagerapi.infra.migration;

import com.qima.promanagerapi.application.domain.Category;
import com.qima.promanagerapi.application.domain.Product;
import com.qima.promanagerapi.application.domain.User;
import com.qima.promanagerapi.application.enums.RoleEnum;
import com.qima.promanagerapi.application.ports.CategoryServiceAdapter;
import com.qima.promanagerapi.application.ports.ProductServiceAdapter;
import com.qima.promanagerapi.application.ports.UserServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class MigrationCommand implements CommandLineRunner {

    private final UserServiceAdapter userServiceAdapter;
    private final CategoryServiceAdapter categoryServiceAdapter;
    private final ProductServiceAdapter productServiceAdapter;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Migration started");

        userServiceAdapter.create(new User(null, "User Manager", "user.admin", "password", RoleEnum.ADMIN));
        userServiceAdapter.create(new User(null, "User Admin", "user.user", "password", RoleEnum.USER));
        Category eletronics = categoryServiceAdapter.create(new Category(null, "Eletronic"));
        categoryServiceAdapter.create(new Category(null, "Automotive"));
        categoryServiceAdapter.create(new Category(null, "Groceries"));

        // subcategories
        categoryServiceAdapter.create(new Category(null, "Smartphones", 1L));
        categoryServiceAdapter.create(new Category(null, "Laptops", 1L));

        // products
        Product product1 = new Product(null, "Iphone 12", BigDecimal.valueOf(1000.0), eletronics, "Apple", true);
        productServiceAdapter.create(product1);

        Product product2 = new Product(null, "Monitor", BigDecimal.valueOf(399.0), eletronics, "Philips", true);
        productServiceAdapter.create(product2);

    }
}
