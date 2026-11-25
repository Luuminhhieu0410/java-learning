
package org.example.repositories;

import org.example.entites.ProductStock;

public class ProductStockRepository extends BaseRepository<ProductStock> {

    public ProductStockRepository() {
        super(ProductStock::getId);
    }

    public void reduceStock(String stockId , float count){
        var product = this.data.get(stockId);
        product.setCount(product.getCount() - count);
        this.data.put(stockId,product);
    }
}
