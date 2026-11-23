package org.example.entites;

public class ProductStock {
    private String id;
    private String productId;
    private String stockId;
    private String unitCountId;


    public ProductStock(String id, String productId, String stockId, String unitCountId) {
        this.id = id;
        this.productId = productId;
        this.stockId = stockId;
        this.unitCountId = unitCountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }


    public String getUnitCountId() {
        return unitCountId;
    }

    public void setUnitCountId(String unitCountId) {
        this.unitCountId = unitCountId;
    }
}
