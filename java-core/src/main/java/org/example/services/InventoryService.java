package org.example.services;

import org.example.exceptions.InvalidCodeException;
import org.example.exceptions.OutOfStockException;
import org.example.repositories.ProductStockRepository;

import java.util.Map;

public class InventoryService {

    private final Map<String, Integer> stockQuantities;
    private final ProductStockRepository productStockRepository;

    public InventoryService(
            Map<String, Integer> stockQuantities,
            ProductStockRepository productStockRepository) {
        this.stockQuantities = stockQuantities;
        this.productStockRepository = productStockRepository;
    }

    public void export(String productStockId, int quantity)
            throws InvalidCodeException, OutOfStockException {

        if (productStockId == null || productStockId.isBlank()) {
            throw new InvalidCodeException(productStockId);
        }

        if (!productStockRepository.getAll().stream()
                .anyMatch(item -> item.getId().equals(productStockId))) {
            throw new InvalidCodeException(productStockId);
        }

        int available = stockQuantities.getOrDefault(productStockId, 0);

        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng xuất phải lớn hơn 0");
        }

        if (available < quantity) {
            throw new OutOfStockException(productStockId, available, quantity);
        }

        stockQuantities.put(productStockId, available - quantity);
    }
}
