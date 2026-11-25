package org.example.exceptions;

public class OutOfStockException extends InventoryException {

    public OutOfStockException(String productStockId, float available, float requested) {
        super("Hết tồn kho cho mã " + productStockId
                + ". Tồn kho: " + available
                + ", yêu cầu: " + requested);
    }
}
