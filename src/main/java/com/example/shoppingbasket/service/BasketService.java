package com.example.shoppingbasket.service;

import com.example.shoppingbasket.error.BasketNotFoundException;
import com.example.shoppingbasket.model.Article;
import com.example.shoppingbasket.model.Basket;
import com.example.shoppingbasket.model.Buy1Get1Free;
import com.example.shoppingbasket.model.PercentOff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final InventoryService inventoryService;
    private final List<Basket> baskets = new ArrayList<>();

    public UUID createBasket() {
        var basket = new Basket();
        baskets.add(basket);
        return basket.getId();
    }

    public void scan(UUID basketId, String articleName) {
        var basket = findBasketById(basketId);
        var article = inventoryService.getArticle(articleName);

        basket.getArticles().add(article);
    }

    public Basket findBasketById(UUID basketId) {
        return baskets.stream()
                .filter(it -> it.getId().equals(basketId))
                .findFirst()
                .orElseThrow(() -> new BasketNotFoundException("Basket with id " + basketId + " not found."));
    }

    public BigDecimal total(UUID basketId) {
        var basket = findBasketById(basketId);
        var articles = basket.getArticles();
        var sum = articles.stream() //
                .map(Article::getPrice) //
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        for (var buy1Get1Free  : basket.getBuy1Get1Frees()) {
            var discountedArticle = buy1Get1Free.getArticle();
            var discountedArticlesInBasket = articles.stream().filter(article -> article.getName().equals(discountedArticle.getName())).count();
            if (discountedArticlesInBasket > 1) {
                sum = sum.subtract(discountedArticle.getPrice());
            }
        }

        for (var percentOff  : basket.getPercentOff()) {
            var discountedArticle = percentOff.getArticle();
            var discountedArticlesInBasket = articles.stream().filter(article -> article.getName().equals(discountedArticle.getName())).count();
            for (int i = 0; i < discountedArticlesInBasket; i++) {
                sum = sum.subtract(discountedArticle.getPrice().multiply(BigDecimal.valueOf(0.1)));
            }
        }

        MathContext mathContext = new MathContext(4);
        sum = sum.round(mathContext);

        return sum;
    }

    public void addBuy1Get1Free(UUID basketId, String articleName) {
        var basket = findBasketById(basketId);
        var article = inventoryService.getArticle(articleName);
        var coupon = new Buy1Get1Free(article);
        basket.getBuy1Get1Frees().add(coupon);
    }

    public void add10PercentOff(UUID basketId, String articleName) {
        var basket = findBasketById(basketId);
        var article = inventoryService.getArticle(articleName);
        var coupon = new PercentOff(article);
        basket.getPercentOff().add(coupon);
    }
}
