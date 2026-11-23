package org.example;

import org.example.collections.InventoryCollectionService;
import org.example.exceptions.InvalidCodeException;
import org.example.exceptions.OutOfStockException;
import org.example.entites.*;
import org.example.services.InventoryService;
import org.example.repositories.ProductRepository;
import org.example.repositories.ProductStockRepository;
import org.example.repositories.StockRepository;
import org.example.repositories.UnitRepository;

import java.io.BufferedReader;
import java.io.StringReader;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

public class Main {
    public static int test(int x){
        return x;
    }

    public static void main(String[] args) {
        Function<Integer,Integer> plus = Main::test;
        System.out.println(plus.andThen(x -> x + 1).apply(1));

        ProductRepository productRepository = new ProductRepository();
        StockRepository stockRepository = new StockRepository();
        UnitRepository unitRepository = new UnitRepository();
        ProductStockRepository productStockRepository = new ProductStockRepository();

        DataInitializer.initialize(
                productRepository,
                stockRepository,
                unitRepository,
                productStockRepository
        );

        InventoryCollectionService collectionService = new InventoryCollectionService();

        System.out.println("Tim theo ten 'a':");
        collectionService.findByName(productRepository.getAll(), "a")
                .forEach(product -> System.out.println(product.getName()));

        System.out.println("San pham sap xep:");
        collectionService.sortByName(productRepository.getAll())
                .forEach(product -> System.out.println(product.getName()));

        System.out.println("Nhom theo kho:");
        collectionService.groupProductStockByStockId(productStockRepository.getAll())
                .forEach((stockId, productStocks) ->
                        System.out.println(stockId + ": " + productStocks.size())
                );

        System.out.println("Danh sach kho duy nhat:");
        collectionService.getUniqueStockIds(productStockRepository.getAll())
                .forEach(System.out::println);

        Map<String, Integer> stockQuantities = new HashMap<>();
        stockQuantities.put("0", 5);
        stockQuantities.put("1", 2);

        InventoryService inventoryService = new InventoryService(
                stockQuantities,
                productStockRepository
        );

        String[] exportCases = {"0", "9", "1"};
        int[] exportQuantities = {2, 1, 3};

        for (int i = 0; i < exportCases.length; i++) {
            try {
                inventoryService.export(exportCases[i], exportQuantities[i]);
                System.out.println("Xuất kho thành công: " + exportCases[i]);
            } catch (InvalidCodeException | OutOfStockException exception) {
                System.err.println("[BUSINESS ERROR] " + exception.getMessage());
            } catch (IllegalArgumentException exception) {
                System.err.println("[INPUT ERROR] " + exception.getMessage());
            } finally {
                System.out.println("Kết thúc lần xử lý nghiệp vụ " + (i + 1));
            }
        }

        try (BufferedReader reader = new BufferedReader(
                new StringReader("Dữ liệu kho hợp lệ"))) {
            System.out.println(reader.readLine());
        } catch (java.io.IOException exception) {
            System.err.println("[SYSTEM ERROR] Không thể đọc dữ liệu: "
                    + exception.getMessage());
        }
    }
}
class DataInitializer {

    public static void initialize(
            ProductRepository productRepository,
            StockRepository stockRepository,
            UnitRepository unitRepository,
            ProductStockRepository productStockRepository
    ) {
        

        productRepository.add(new Product("0", "Quan"));
        productRepository.add(new Product("1", "Ao"));
        productRepository.add(new Product("2", "Dep"));


        stockRepository.add(new Stock("0", "Kho Ha Noi", "Ha Noi", 1));
        stockRepository.add(new Stock("1", "Kho Phu Tho", "Phu Tho", 2));
        stockRepository.add(new Stock("2", "Kho Vinh Phuc", "Vinh Phuc", 3));


        unitRepository.add(new Unit("0", "Chiec", "cai", "Don vi chiec"));
        unitRepository.add(new Unit("1", "Doi", "doi", "Don vi doi"));
        unitRepository.add(new Unit("2", "Hop", "hop", "Don vi hop"));

        productStockRepository.add(new ProductStock("0", "0", "0", "0"));
        productStockRepository.add(new ProductStock("1", "1", "0", "0"));
        productStockRepository.add(new ProductStock("2", "2", "1", "1"));
    }
}