package com.example.shoppingbasket.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class PercentOff {

    @NonNull
    private Article article;
}
