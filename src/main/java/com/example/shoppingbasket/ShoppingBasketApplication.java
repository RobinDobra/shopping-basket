package com.example.shoppingbasket;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ShoppingBasketApplication implements CommandLineRunner {

//    private final BasketRepository basketRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShoppingBasketApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Load initial data into the database

//        var article1 = new Article(UUID.randomUUID(), "A0001", BigDecimal.valueOf(12.99));
//        var article2 = new Article(UUID.randomUUID(), "A0002", BigDecimal.valueOf(3.99));
//        inventory.setArticles(List.of(article1, article2));
//        inventoryRepository.save(inventory);
    }

}
