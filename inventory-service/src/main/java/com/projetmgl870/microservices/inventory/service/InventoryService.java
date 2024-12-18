package com.projetmgl870.microservices.inventory.service;

import com.projetmgl870.microservices.inventory.model.Inventory;
import com.projetmgl870.microservices.inventory.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    /**
     * Met à jour l'inventaire en réduisant la quantité pour le produit donné.
     * @param skuCode le code SKU du produit.
     * @param quantity la quantité à déduire.
     */
    public void updateInventory(String skuCode, int quantity) {
        log.info("Updating inventory for SKU: {}, reducing quantity by {}", skuCode, quantity);
        try {
            Inventory inventory = inventoryRepository.findBySkuCode(skuCode);
            if (inventory == null) {
                log.warn("No inventory found for SKU: {}", skuCode);
                throw new RuntimeException("Inventory not found for SKU: " + skuCode);
            }
            int updatedQuantity = inventory.getQuantity() - quantity;
            inventory.setQuantity(updatedQuantity);
            inventoryRepository.save(inventory);
            log.info("Successfully updated inventory for SKU: {}. New quantity: {}", skuCode, updatedQuantity);
        } catch (Exception e) {
            log.error("Failed to update inventory for SKU: {}", skuCode, e);
            throw new RuntimeException("Error while updating inventory for SKU: " + skuCode, e);
        }
    }

    /**
     * Vérifie si un produit est en stock.
     * @param skuCode le code SKU du produit.
     * @param quantity la quantité demandée.
     * @return true si en stock, sinon false.
     */
    public boolean isInStock(String skuCode, Integer quantity) {
        log.info("Checking stock for SKU: {}, requested quantity: {}", skuCode, quantity);
        try {
            boolean inStock = inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
            if (inStock) {
                log.info("SKU: {} is in stock with sufficient quantity.", skuCode);
            } else {
                log.warn("SKU: {} is not in stock or has insufficient quantity.", skuCode);
            }
            return inStock;
        } catch (Exception e) {
            log.error("Error while checking stock for SKU: {}", skuCode, e);
            throw new RuntimeException("Error while checking stock for SKU: " + skuCode, e);
        }
    }

    public int getInventory(String skuCode) {
        return 0;
    }
}
