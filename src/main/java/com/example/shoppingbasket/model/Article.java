package com.example.shoppingbasket.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Article {

    @NonNull
    private String name;

    @NonNull
    private BigDecimal price;
}
