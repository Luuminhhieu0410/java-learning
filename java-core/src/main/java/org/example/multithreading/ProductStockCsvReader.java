package org.example.multithreading;

import org.example.entites.ProductStock;
import org.example.fileIOandTime.InventoryCsvReport;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ProductStockCsvReader {
    private static final String CSV_HEADER =
            "id,productId,stockId,unitId,count";

    private ProductStockCsvReader() {
    }

    public static List<ProductStock> read(Path inputPath) throws IOException {
        var productStocks = new ArrayList<ProductStock>();
        try (BufferedReader reader = Files.newBufferedReader(
                inputPath,
                StandardCharsets.UTF_8
        )) {
            var header = reader.readLine();
            if (!CSV_HEADER.equals(header)) {
                throw new IOException(
                        "CSV header không hợp lệ trong file: " + inputPath
                );
            }

            var lineNumber = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }

                var fields = InventoryCsvReport.parseCsvLine(line, lineNumber);
                if (fields.size() != 5) {
                    throw new IOException(
                            "Dòng " + lineNumber + " phải có đúng 5 cột"
                    );
                }

                var id = fields.get(0).strip();
                var productId = fields.get(1).strip();
                var stockId = fields.get(2).strip();
                var unitId = fields.get(3).strip();
                if (id.isEmpty() || productId.isEmpty() || stockId.isEmpty()
                        || unitId.isEmpty()) {
                    throw new IOException(
                            "Dòng " + lineNumber + " có ID bị trống"
                    );
                }

                float count;
                try {
                    count = Float.parseFloat(fields.get(4).strip());
                } catch (NumberFormatException exception) {
                    throw new IOException(
                            "Count không hợp lệ ở dòng " + lineNumber,
                            exception
                    );
                }
                if (!Float.isFinite(count) || count < 0) {
                    throw new IOException(
                            "Count phải là số hữu hạn không âm ở dòng "
                                    + lineNumber
                    );
                }

                productStocks.add(new ProductStock(
                        id,
                        productId,
                        stockId,
                        unitId,
                        count
                ));
            }
        }
        return productStocks;
    }
}
