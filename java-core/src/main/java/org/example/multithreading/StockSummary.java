package org.example.multithreading;

import java.math.BigDecimal;

public record StockSummary(long rowCount, BigDecimal totalCount) {
    public StockSummary add(BigDecimal count) {
        return new StockSummary(rowCount + 1, totalCount.add(count));
    }
}
