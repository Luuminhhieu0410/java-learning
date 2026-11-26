package org.example.multithreading;

import org.example.repositories.ProductStockRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Path DEFAULT_INPUT =
            Path.of("data/10000_product_stock.csv");

    public static void main(String[] args) throws IOException {
        var inputPath = DEFAULT_INPUT;
        var useVirtualThreads = false;

        for (var argument : args) {
            if (argument.equals("--virtual")) {
                useVirtualThreads = true;
            } else if (argument.startsWith("--")) {
                throw new IllegalArgumentException(
                        "Tham số không được hỗ trợ: " + argument
                );
            } else {
                inputPath = Path.of(argument);
            }
        }

        var productStocks = ProductStockCsvReader.read(inputPath);
        var executor = createExecutor(useVirtualThreads);
        ImportResult result;
        try (executor) {
            result = new ConcurrentInventoryImporter(executor)
                    .importProductStocks(productStocks);
        }

        var productStockRepository = new ProductStockRepository();
        result.productStocks().forEach(productStockRepository::add);

        System.out.println("Đã import vào repository: "
                + productStockRepository.getAll().size() + " dòng");
        System.out.println("Tổng tồn kho: " + result.totalCount());
        System.out.println("Số kho: " + result.byStock().size());
        new TreeMap<>(result.byStock()).forEach((stock, summary) ->
                System.out.printf(
                        "%s: %d dòng, tổng %s%n",
                        stock,
                        summary.rowCount(),
                        summary.totalCount()
                )
        );
    }

    private static ExecutorService createExecutor(boolean useVirtualThreads) {
        if (useVirtualThreads) {
            return Executors.newVirtualThreadPerTaskExecutor();
        }
        var workerCount = Math.max(
                1,
                Math.min(Runtime.getRuntime().availableProcessors(), 8)
        );
        return Executors.newFixedThreadPool(workerCount);
    }
}
