
package org.example.modernsystax;

import org.example.entites.Product;
import org.example.entites.ProductStock;
import org.example.entites.Stock;
import org.example.entites.Unit;

import org.example.repositories.ProductRepository;
import org.example.repositories.ProductStockRepository;
import org.example.repositories.StockRepository;
import org.example.repositories.UnitRepository;

import java.util.List;

public class Run {

    private static void logInventoryError(InventoryException exception) {

        var category = switch (exception) {
            case InvalidCodeException ignored -> "INVALID CODE";
            case OutOfStockException ignored -> "OUT OF STOCK";
            default -> "UNKNOWN ERROR";
        };

        var action = switch (exception) {
            case InvalidCodeException e ->
                    "Kiểm tra lại mã " + e.code() + ".";
            case OutOfStockException ignored ->
                    "Kiểm tra số lượng tồn kho.";
            default -> "Kiểm tra lại nghiệp vụ kho.";
        };

        System.err.println("[INVENTORY ERROR] " + category);
        System.err.println("Message: " + exception.getMessage());
        System.err.println("Gợi ý: " + action);
    }

    static void export(
            ProductStockRepository repository,
            ExportRequest request
    ) throws InventoryException {

        var productStock = repository.getById(
                request.ProductStockId()
        );

        if (productStock == null) {
            throw new InvalidCodeException(
                    request.ProductStockId()
            );
        }

        if (productStock.getCount() < request.quantityExport()) {
            throw new OutOfStockException(
                    request.ProductStockId(),
                    (int) productStock.getCount(),
                    request.quantityExport()
            );
        }

        repository.reduceStock(
                request.ProductStockId(),
                request.quantityExport()
        );

        System.out.println("Export success: " + request.ProductStockId());
    }

    public static void main(String[] args) {

        ProductRepository productRepository = new ProductRepository();
        StockRepository stockRepository = new StockRepository();
        UnitRepository unitRepository = new UnitRepository();
        ProductStockRepository productStockRepository =
                new ProductStockRepository();

        DataInitializer.initialize(
                productRepository,
                stockRepository,
                unitRepository,
                productStockRepository
        );

        var exportRequests = List.of(
                new ExportRequest("0", 1),
                new ExportRequest("0", 10)
        );

        productStockRepository.getAll()
                .forEach(System.out::println);

        for (var exportRequest : exportRequests) {

            try {
                Run.export(productStockRepository, exportRequest);

            } catch (InventoryException exception) {
                Run.logInventoryError(exception);
            }
        }

        productStockRepository.getAll()
                .forEach(System.out::println);

        System.out.println("Continue log");
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

        stockRepository.add(
                new Stock("0", "Kho Ha Noi", "Ha Noi", 1)
        );
        stockRepository.add(
                new Stock("1", "Kho Phu Tho", "Phu Tho", 2)
        );
        stockRepository.add(
                new Stock("2", "Kho Vinh Phuc", "Vinh Phuc", 3)
        );

        unitRepository.add(
                new Unit("0", "Chiec", "cai", "Don vi chiec")
        );
        unitRepository.add(
                new Unit("1", "Doi", "doi", "Don vi doi")
        );
        unitRepository.add(
                new Unit("2", "Hop", "hop", "Don vi hop")
        );

        productStockRepository.add(
                new ProductStock("0", "0", "0", "0", 5.0f)
        );
        productStockRepository.add(
                new ProductStock("1", "1", "0", "0", 2.0f)
        );
        productStockRepository.add(
                new ProductStock("2", "2", "1", "1", 0.0f)
        );
    }
}
