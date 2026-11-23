package org.example.exceptions;

public class OutOfStockException extends InventoryException {

    public OutOfStockException(String productStockId, int available, int requested) {
        super("Hết tồn kho cho mã " + productStockId
                + ". Tồn kho: " + available
                + ", yêu cầu: " + requested);
    }
}
