package org.example.multithreading;

import org.example.entites.ProductStock;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class ConcurrentInventoryImporter {
    private final Executor executor;

    public ConcurrentInventoryImporter(Executor executor) {
        this.executor = executor;
    }

    public ImportResult importProductStocks(List<ProductStock> productStocks) {
        var importedById = new ConcurrentHashMap<String, ProductStock>();
        var summaryByStock = new ConcurrentHashMap<String, StockSummary>();
        var tasks = new CompletableFuture<?>[productStocks.size()];

        for (var index = 0; index < productStocks.size(); index++) {
            var productStock = productStocks.get(index);
            tasks[index] = CompletableFuture.runAsync(() -> {
                var previous = importedById.putIfAbsent(
                        productStock.getId(),
                        productStock
                );
                if (previous != null) {
                    throw new IllegalArgumentException(
                            "ProductStock ID bị trùng: "
                                    + productStock.getId()
                    );
                }

                var count = new BigDecimal(
                        Float.toString(productStock.getCount())
                );
                summaryByStock.compute(
                        productStock.getStockId(),
                        (stockId, previousSummary) -> previousSummary == null
                                ? new StockSummary(1, count)
                                : previousSummary.add(count)
                );
            }, executor);
        }

        CompletableFuture.allOf(tasks).join();

        var totalCount = summaryByStock.values().stream()
                .map(StockSummary::totalCount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ImportResult(
                importedById.size(),
                totalCount,
                summaryByStock,
                List.copyOf(importedById.values())
        );
    }
}
