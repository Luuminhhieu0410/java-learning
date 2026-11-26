package org.example.multithreading;

import org.example.entites.ProductStock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record ImportResult(
        int rowCount,
        BigDecimal totalCount,
        Map<String, StockSummary> byStock,
        List<ProductStock> productStocks
) {
    public ImportResult {
        byStock = Map.copyOf(byStock);
        productStocks = List.copyOf(productStocks);
    }
}
