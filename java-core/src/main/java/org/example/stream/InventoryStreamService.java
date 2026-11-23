package org.example.stream;

import org.example.collections.BaseInventoryService;
import org.example.entites.Product;
import org.example.entites.ProductStock;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InventoryStreamService extends BaseInventoryService {

    @Override
    public List<Product> findByName(List<Product> products, String keyword) {
        return products.stream()
                .filter(p -> p.getName() != null
                        && p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> sortByName(List<Product> products) {
        return products.stream()
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<ProductStock>> groupProductStockByStockId(
            List<ProductStock> productStocks) {

        return productStocks.stream()
                .collect(Collectors.groupingBy(ProductStock::getStockId));
    }

    @Override
    public Set<String> getUniqueStockIds(List<ProductStock> productStocks) {
        return productStocks.stream()
                .map(ProductStock::getStockId)
                .collect(Collectors.toSet());
    }
}
