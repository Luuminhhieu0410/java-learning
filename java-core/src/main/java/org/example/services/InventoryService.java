package org.example.services;

import org.example.exceptions.InvalidCodeException;
import org.example.exceptions.OutOfStockException;
import org.example.entites.ProductStock;
import org.example.repositories.ProductStockRepository;

public class InventoryService {

    private final ProductStockRepository productStockRepository;

    public InventoryService(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    public void export(String productStockId, float quantity)
            throws InvalidCodeException, OutOfStockException {

        if (productStockId == null || productStockId.isBlank()) {
            throw new InvalidCodeException(productStockId);
        }

        ProductStock productStock = productStockRepository.getAll().stream()
                .filter(item -> item.getId().equals(productStockId))
                .findFirst()
                .orElseThrow(() -> new InvalidCodeException(productStockId));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng xuất phải lớn hơn 0");
        }

        float available = productStock.getCount();
        if (available < quantity) {
            throw new OutOfStockException(productStockId, available, quantity);
        }

        productStock.setCount(available - quantity);
    }
}
