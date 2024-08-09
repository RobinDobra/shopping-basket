package com.example.shoppingbasket.controller;

import com.example.shoppingbasket.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/createbasket")
    public ResponseEntity<UUID> createBasket() {
        return ResponseEntity.ok(basketService.createBasket());
    }

    @PostMapping("/scan/basket/{basketId}/article/{articleName}")
    public ResponseEntity scanArticle(@PathVariable UUID basketId, @PathVariable String articleName) {
        basketService.scan(basketId, articleName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total/basket/{basketId}")
    public ResponseEntity<BigDecimal> getTotal(@PathVariable UUID basketId) {
        return ResponseEntity.ok(basketService.total(basketId));
    }

    @PostMapping("/buy1get1free/basket/{basketId}/article/{articleName}")
    public ResponseEntity buy1Get1Free(@PathVariable UUID basketId, @PathVariable String articleName) {
        basketService.addBuy1Get1Free(basketId, articleName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/10percentoff/basket/{basketId}/article/{articleName}")
    public ResponseEntity percentOff(@PathVariable UUID basketId, @PathVariable String articleName) {
        basketService.add10PercentOff(basketId, articleName);
        return ResponseEntity.ok().build();
    }
}
