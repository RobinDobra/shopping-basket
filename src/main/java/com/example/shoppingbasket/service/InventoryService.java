package com.example.shoppingbasket.service;

import com.example.shoppingbasket.error.ArticleNotFoundException;
import com.example.shoppingbasket.error.BasketNotFoundException;
import com.example.shoppingbasket.model.Article;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class InventoryService {

    private final Map<String, Article> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("A0001", new Article("A0001", BigDecimal.valueOf(12.99)));
        inventory.put("A0002", new Article("A0002", BigDecimal.valueOf(3.99)));
    }

    public Article getArticle(String code) {
        if (!inventory.containsKey(code)) {
            throw new ArticleNotFoundException("Article with name " + code + " not found.");
        }
        return inventory.get(code);
    }
}
