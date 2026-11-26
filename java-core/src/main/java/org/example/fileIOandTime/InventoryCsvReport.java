package org.example.fileIOandTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class InventoryCsvReport {
    private static final String CSV_HEADER =
            "product_id,product_name,stock_name,unit,quantity";
    private static final String REPORT_HEADER =
            "report_date,generated_at,zone_id,product_id,product_name,stock_name,unit,quantity";

    public static void main(String[] args) throws IOException {
        var inputPath = args.length > 0
                ? Path.of(args[0])
                : Path.of("data/products.csv");
        var outputDirectory = args.length > 1
                ? Path.of(args[1])
                : Path.of("data/reports");

        var reportPath = generateReport(
                inputPath,
                outputDirectory,
                ZoneId.of("Asia/Ho_Chi_Minh")
        );
        System.out.println("Đã ghi báo cáo tồn kho: " + reportPath);
    }

    static Path generateReport(
            Path inputPath,
            Path outputDirectory,
            ZoneId zoneId
    ) throws IOException {
        var products = readProducts(inputPath);
        Instant generatedAt = Instant.now();
        LocalDate reportDate = generatedAt.atZone(zoneId).toLocalDate();
        var reportPath = outputDirectory.resolve(
                "inventory-report-" + reportDate + ".csv"
        );

        var report = new StringBuilder(REPORT_HEADER).append('\n');
        for (var product : products) {
            report.append(reportDate).append(',')
                    .append(generatedAt).append(',')
                    .append(csvField(zoneId.getId())).append(',')
                    .append(csvField(product.id())).append(',')
                    .append(csvField(product.name())).append(',')
                    .append(csvField(product.stockName())).append(',')
                    .append(csvField(product.unit())).append(',')
                    .append(product.quantity()).append('\n');
        }

        Files.createDirectories(outputDirectory);
        Files.writeString(reportPath, report, StandardCharsets.UTF_8);
        return reportPath;
    }

    public static List<InventoryProduct> readProducts(Path inputPath)
            throws IOException {
        var products = new ArrayList<InventoryProduct>();
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

                var fields = parseCsvLine(line, lineNumber);
                if (fields.size() != 5) {
                    throw new IOException(
                            "Dòng " + lineNumber + " phải có đúng 5 cột"
                    );
                }

                var productId = fields.get(0).strip();
                var productName = fields.get(1).strip();
                var stockName = fields.get(2).strip();
                var unit = fields.get(3).strip();
                if (productId.isEmpty() || productName.isEmpty()
                        || stockName.isEmpty() || unit.isEmpty()) {
                    throw new IOException(
                            "Dòng " + lineNumber
                                    + " có trường sản phẩm/kho/đơn vị trống"
                    );
                }

                float quantity;
                try {
                    quantity = Float.parseFloat(fields.get(4).strip());
                } catch (NumberFormatException exception) {
                    throw new IOException(
                            "Số lượng không hợp lệ ở dòng " + lineNumber,
                            exception
                    );
                }
                if (!Float.isFinite(quantity) || quantity < 0) {
                    throw new IOException(
                            "Số lượng phải là số hữu hạn không âm ở dòng "
                                    + lineNumber
                    );
                }

                products.add(new InventoryProduct(
                        productId,
                        productName,
                        stockName,
                        unit,
                        quantity
                ));
            }
        }
        return products;
    }

    public static List<String> parseCsvLine(String line, int lineNumber)
            throws IOException {
        var fields = new ArrayList<String>();
        var field = new StringBuilder();
        var inQuotes = false;
        var quoteClosed = false;

        for (var index = 0; index < line.length(); index++) {
            var character = line.charAt(index);
            if (inQuotes) {
                if (character == '"') {
                    if (index + 1 < line.length()
                            && line.charAt(index + 1) == '"') {
                        field.append('"');
                        index++;
                    } else {
                        inQuotes = false;
                        quoteClosed = true;
                    }
                } else {
                    field.append(character);
                }
            } else if (quoteClosed) {
                if (character != ',') {
                    throw new IOException(
                            "Dữ liệu sau dấu quote không hợp lệ ở dòng "
                                    + lineNumber
                    );
                }
                fields.add(field.toString());
                field.setLength(0);
                quoteClosed = false;
            } else if (character == ',') {
                fields.add(field.toString());
                field.setLength(0);
            } else if (character == '"') {
                if (!field.isEmpty()) {
                    throw new IOException(
                            "Dấu quote không hợp lệ ở dòng " + lineNumber
                    );
                }
                inQuotes = true;
            } else {
                field.append(character);
            }
        }

        if (inQuotes) {
            throw new IOException(
                    "Thiếu dấu quote đóng ở dòng " + lineNumber
            );
        }
        fields.add(field.toString());
        return fields;
    }

    public static String csvField(String value) {
        if (value.indexOf(',') >= 0 || value.indexOf('"') >= 0
                || value.indexOf('\n') >= 0 || value.indexOf('\r') >= 0) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    public record InventoryProduct(
            String id,
            String name,
            String stockName,
            String unit,
            float quantity
    ) {
    }
}
