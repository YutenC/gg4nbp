package gg.nbp.web.shop.shopproduct.config;

import gg.nbp.web.shop.shopproduct.entity.Product;
import gg.nbp.web.shop.shopproduct.entity.ProductImage;
import gg.nbp.web.shop.shopproduct.util.CreateProductDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductDBConfig {

    @Bean
    public CreateProductDB<Product, ProductImage> createProductDB() {
        return new CreateProductDB<>(Product.class, ProductImage.class);
    }
}
