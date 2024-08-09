package com.example.shoppingbasket.model;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Basket {

    @NonNull
    private UUID id = UUID.randomUUID();

    private List<Article> articles = new ArrayList<>();

    private List<Buy1Get1Free> buy1Get1Frees = new ArrayList<>();

    private List<PercentOff> percentOff = new ArrayList<>();
}
