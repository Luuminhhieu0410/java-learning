
package org.example.repositories;

import org.example.entites.ProductStock;

public class ProductStockRepository extends BaseRepository<ProductStock> {

    public ProductStockRepository() {
        super(ProductStock::getId);
    }
}
