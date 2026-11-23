
package org.example.collections;

import org.example.entites.Product;
import org.example.entites.ProductStock;

import java.util.*;

public abstract class BaseInventoryService {

    public abstract List<Product> findByName(
            List<Product> products,
            String keyword
    );

    public abstract List<Product> sortByName(
            List<Product> products
    );

    public abstract Map<String, List<ProductStock>> groupProductStockByStockId(
            List<ProductStock> productStocks
    );

    public abstract Set<String> getUniqueStockIds(
            List<ProductStock> productStocks
    );
}