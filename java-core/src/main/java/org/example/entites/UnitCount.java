package org.example.entites;

public class UnitCount {
    public String id;
    public String productStockId;
    public String unitId;
    public String val;

    public UnitCount(String id, String productStockId, String unitId, String val) {
        this.id = id;
        this.productStockId = productStockId;
        this.unitId = unitId;
        this.val = val;
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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
