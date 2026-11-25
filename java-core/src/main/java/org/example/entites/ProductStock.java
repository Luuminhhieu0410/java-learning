package org.example.entites;

public class ProductStock {
    private String id;
    private String productId;
    private String stockId;
    private String unitId;
    private float count;


    public ProductStock(String id, String productId, String stockId, String unitId, float count) {
        this.id = id;
        this.productId = productId;
        this.stockId = stockId;
        this.unitId = unitId;
        this.count = count;
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


    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ProductStock{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", stockId='" + stockId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", count=" + count +
                '}';
    }
}
