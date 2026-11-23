package org.example.collections;

import org.example.entites.Product;
import org.example.entites.ProductStock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class InventoryCollectionService extends BaseInventoryService {

    public List<Product> findByName(List<Product> products, String keyword) {
        List<Product> result = new ArrayList<>();
        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);

        for (Product product : products) {
            String productName = product.getName();

            if (productName != null
                    && productName.toLowerCase(Locale.ROOT).contains(normalizedKeyword)) {
                result.add(product);
            }
        }

        return result;
    }

    public List<Product> sortByName(List<Product> products) {
        List<Product> result = new ArrayList<>(products);
        Collections.sort(result, (first, second) ->
                first.getName().compareToIgnoreCase(second.getName())
        );
        return result;
    }

    public Map<String, List<ProductStock>> groupProductStockByStockId(List<ProductStock> productStocks) {
        Map<String, List<ProductStock>> result = new HashMap<>();

        for (ProductStock productStock : productStocks) {
            result.computeIfAbsent(
                    productStock.getStockId(),
                    stockId -> new ArrayList<>()
            ).add(productStock);
        }

        return result;
    }

    public Set<String> getUniqueStockIds(List<ProductStock> productStocks) {
        Set<String> stockIds = new LinkedHashSet<>();

        for (ProductStock productStock : productStocks) {
            stockIds.add(productStock.getStockId());
        }

        return stockIds;
    }
}
