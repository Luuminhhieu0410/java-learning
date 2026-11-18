package org.example.entites;

public class AdjustProductStock {
    private String id;
    private String productStockId;
    private String adjustId;
    private int quantity;

    public AdjustProductStock(String id, String productStockId, String adjustId, int quantity) {
        this.id = id;
        this.productStockId = productStockId;
        this.adjustId = adjustId;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductStockId() {
        return productStockId;
    }

    public void setProductStockId(String productStockId) {
        this.productStockId = productStockId;
    }

    public String getAdjustId() {
        return adjustId;
    }

    public void setAdjustId(String adjustId) {
        this.adjustId = adjustId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
