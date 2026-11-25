package org.example.modernsystax;

public non-sealed class OutOfStockException extends InventoryException {
    public OutOfStockException(String productStockId, int available, int requested) {
        super("Hết tồn kho cho mã " + productStockId
                + ". Tồn kho: " + available
                + ", yêu cầu: " + requested);
    }

    public OutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
