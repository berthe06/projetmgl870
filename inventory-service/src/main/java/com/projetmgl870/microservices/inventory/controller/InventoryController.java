package com.projetmgl870.microservices.inventory.controller;

import com.projetmgl870.microservices.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    // update the inventory by skuCode and quantity
    public void updateInventory(String skuCode, int quantity) {
        inventoryService.updateInventory(skuCode, quantity);
    }

    // check if the inventory is in stock
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        return inventoryService.isInStock(skuCode, quantity);
    }

    // get the inventory by skuCode
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public int getInventory(@RequestParam String skuCode) {
        return inventoryService.getInventory(skuCode);
    }
}
