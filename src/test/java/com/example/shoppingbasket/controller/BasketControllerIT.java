package com.example.shoppingbasket.controller;

import com.example.shoppingbasket.service.BasketService;
import com.example.shoppingbasket.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BasketControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private BasketService basketService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createBasket_shouldCreateBasket_AndReturn200() throws Exception {

        var mockRequest = MockMvcRequestBuilders.get("/api/v1/createbasket")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        var result = mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).andReturn();

        var content = result.getResponse().getContentAsString((StandardCharsets.UTF_8));

        assertNotNull(content);
    }

    @Test
    void scan_shouldAddArticle_AndReturn200() throws Exception {
        var articleName = "A0002";
        var basketId = basketService.createBasket();
        var basket = basketService.findBasketById(basketId);

        assertTrue(basket.getArticles().isEmpty());

        mockMvc.perform(
                        post("/api/v1/scan/basket/" + basketId + "/article/" + articleName)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        basket = basketService.findBasketById(basketId);

        assertEquals(1, basket.getArticles().size());
        assertEquals(articleName, basket.getArticles().getFirst().getName());
    }

    @Test
    void total_shouldCalculateSumCorrectly_AndReturn200() throws Exception {
        var basketId = basketService.createBasket();
        var basket = basketService.findBasketById(basketId);

        var article1 = inventoryService.getArticle("A0002");
        var article2 = inventoryService.getArticle("A0001");
        var article3 = inventoryService.getArticle("A0002");

        basket.setArticles(List.of(article1, article2, article3));

        var result = mockMvc
                .perform(get("/api/v1/total/basket/" + basketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var content = result.getResponse().getContentAsString((StandardCharsets.UTF_8));

        assertNotNull(content);
        assertEquals("20.97", content);
    }

    @Test
    void addBuy1Get1Free_shouldAddCoupon_AndReturn200() throws Exception {
        var basketId = basketService.createBasket();
        var articleName = "A0001";

        mockMvc.perform(
                post("/api/v1/buy1get1free/basket/" + basketId + "/article/" + articleName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var basket = basketService.findBasketById(basketId);

        assertEquals(1, basket.getBuy1Get1Frees().size());
        assertEquals(articleName, basket.getBuy1Get1Frees().getFirst().getArticle().getName());
    }

    @Test
    void total_shouldCalculateGet1Buy1FreeSumCorrectly_AndReturn200() throws Exception {
        var basketId = basketService.createBasket();
        var basket = basketService.findBasketById(basketId);
        var articleName = "A0002";

        var article1 = inventoryService.getArticle("A0002");
        var article2 = inventoryService.getArticle("A0001");
        var article3 = inventoryService.getArticle("A0002");

        basket.setArticles(List.of(article1, article2, article3));

        mockMvc.perform(
                        post("/api/v1/buy1get1free/basket/" + basketId + "/article/" + articleName)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var result = mockMvc
                .perform(get("/api/v1/total/basket/" + basketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var content = result.getResponse().getContentAsString((StandardCharsets.UTF_8));

        assertNotNull(content);
        assertEquals("16.98", content);
    }

    @Test
    void total_shouldCalculatePercentOfSumCorrectly_AndReturn200() throws Exception {
        var basketId = basketService.createBasket();
        var basket = basketService.findBasketById(basketId);
        var articleName = "A0001";

        var article1 = inventoryService.getArticle("A0002");
        var article2 = inventoryService.getArticle("A0001");
        var article3 = inventoryService.getArticle("A0002");

        basket.setArticles(List.of(article1, article2, article3));

        mockMvc.perform(
                        post("/api/v1/10percentoff/basket/" + basketId + "/article/" + articleName)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var result = mockMvc
                .perform(get("/api/v1/total/basket/" + basketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        var content = result.getResponse().getContentAsString((StandardCharsets.UTF_8));

        assertNotNull(content);
        assertEquals("19.67", content);
    }
}
