# Java Date & Time, đọc/ghi file, Files/Path và UTF-8

Đây là những kiến thức quan trọng trong Java hiện đại (Java 8 trở lên), đặc biệt khi làm ứng dụng quản lý dữ liệu, xử lý ngày tháng và lưu trữ file.


# 1. Java Time API (`java.time`)

Java cung cấp package `java.time` để xử lý ngày giờ. Đây là API nên dùng thay cho `Date` và `Calendar` cũ.

## 1.1. LocalDate – Ngày tháng

Dùng khi chỉ cần ngày, không cần giờ và múi giờ.

java

```java
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();

        LocalDate date = LocalDate.of(2026, 11, 25);

        System.out.println(today);
        System.out.println(date);

        System.out.println(date.getYear());
        System.out.println(date.getMonthValue());
        System.out.println(date.getDayOfMonth());

        // Cộng và trừ ngày
        System.out.println(date.plusDays(7));
        System.out.println(date.minusMonths(1));

        // So sánh
        System.out.println(date.isBefore(today));
        System.out.println(date.isAfter(today));
    }
}
```

Một số phương thức quan trọng:

| Phương thức | Ý nghĩa |
| --- | --- |
| `now()` | Lấy ngày hiện tại |
| `of(y,m,d)` | Tạo ngày |
| `plusDays(n)` | Cộng ngày |
| `minusDays(n)` | Trừ ngày |
| `getYear()` | Lấy năm |
| `getMonthValue()` | Lấy tháng |
| `isBefore()` | Kiểm tra trước |
| `isAfter()` | Kiểm tra sau |

## 1.2. Instant – Thời điểm tuyệt đối

`Instant` biểu diễn một thời điểm trên dòng thời gian, dựa trên UTC.

java

```java
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Instant now = Instant.now();

        System.out.println(now);

        // Cộng thời gian
        Instant future = now.plusSeconds(3600);

        // So sánh
        System.out.println(now.isBefore(future));

        // Khoảng thời gian giữa hai thời điểm
        long seconds = future.getEpochSecond()
                           - now.getEpochSecond();

        System.out.println(seconds);
    }
}
```

Ví dụ kết quả:

text

```text
2026-11-25T08:00:00Z
true
3600
```

`Instant` thường dùng cho:

- Ghi nhận thời điểm tạo dữ liệu.
- Log hệ thống.
- Lưu thời gian sự kiện.
- Đồng bộ thời gian giữa các máy chủ.

## 1.3. ZoneId – Múi giờ

`ZoneId` đại diện cho múi giờ của một khu vực.

Ví dụ: Việt Nam sử dụng múi giờ `Asia/Ho_Chi_Minh` (UTC+7).

java

```java
import java.time.*;

public class Main {
    public static void main(String[] args) {
        ZoneId vietnam = ZoneId.of("Asia/Ho_Chi_Minh");

        ZonedDateTime now = ZonedDateTime.now(vietnam);

        System.out.println(now);

        // Chuyển Instant sang giờ Việt Nam
        Instant instant = Instant.now();

        ZonedDateTime vietnamTime =
                instant.atZone(vietnam);

        System.out.println(vietnamTime);
    }
}
```

Phân biệt ba kiểu dữ liệu:

| Kiểu | Dùng để |
| --- | --- |
| `LocalDate` | Ngày tháng |
| `Instant` | Thời điểm tuyệt đối UTC |
| `ZoneId` | Múi giờ |
| `ZonedDateTime` | Ngày giờ kèm múi giờ |

Ví dụ thực tế:

- Ngày sinh: `LocalDate`.
- Thời điểm đặt hàng: `Instant`.
- Giờ hiển thị tại Việt Nam: `ZonedDateTime`.

# 2. Đọc và ghi file trong Java

Java hỗ trợ đọc và ghi file thông qua nhiều cách.

Cách đơn giản thường gặp là `FileWriter`, `FileReader`, `BufferedReader`, `BufferedWriter`.

## 2.1. Ghi file bằng FileWriter

java

```java
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            FileWriter writer =
                    new FileWriter("data.txt");

            writer.write("Hello Java!\n");
            writer.write("Hoc lap trinh Java");

            writer.close();

            System.out.println("Ghi file thanh cong");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

Lưu ý: `FileWriter` mặc định sử dụng encoding của môi trường nếu không chỉ định encoding.

## 2.2. Đọc file bằng FileReader

java

```java
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader =
                    new BufferedReader(
                        new FileReader("data.txt")
                    );

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

`BufferedReader` giúp đọc dữ liệu theo từng dòng thuận tiện và hiệu quả hơn.

## 2.3. Try-with-resources

Đây là cách nên sử dụng để tự động đóng file sau khi xử lý.

java

```java
import java.io.*;

public class Main {
    public static void main(String[] args) {

        try (BufferedWriter writer =
                 new BufferedWriter(
                     new FileWriter("data.txt")
                 )) {

            writer.write("Java Programming");
            writer.newLine();
            writer.write("File Handling");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

Ưu điểm:

- Không cần gọi `close()` thủ công.
- Tránh rò rỉ tài nguyên.
- Code gọn và an toàn hơn.

# 3. Files và Path (Java NIO)

Từ Java 7, Java cung cấp `java.nio.file` để thao tác file hiện đại và tiện lợi hơn.

## 3.1. Path – Đường dẫn file

`Path` biểu diễn đường dẫn đến file hoặc thư mục.

java

```java
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        Path path = Paths.get("data", "students.txt");

        System.out.println(path);

        System.out.println(path.getFileName());
        System.out.println(path.getParent());
        System.out.println(path.toAbsolutePath());
    }
}
```

Một số phương thức:

| Phương thức | Ý nghĩa |
| --- | --- |
| `getFileName()` | Lấy tên file |
| `getParent()` | Lấy thư mục cha |
| `toAbsolutePath()` | Lấy đường dẫn tuyệt đối |
| `resolve()` | Ghép đường dẫn |
| `normalize()` | Chuẩn hóa đường dẫn |
| `getRoot()` | Lấy thư mục gốc |

## 3.2. Files – Thao tác file

`Files` cung cấp các phương thức để tạo, đọc, ghi, xóa và kiểm tra file.

java

```java
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {

        Path path = Paths.get("data.txt");

        try {
            // Tạo file nếu chưa tồn tại
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            // Kiểm tra file
            System.out.println(Files.exists(path));

            // Kiểm tra có phải file thường không
            System.out.println(Files.isRegularFile(path));

            // Kiểm tra kích thước
            System.out.println(Files.size(path));

            // Xóa file
            // Files.delete(path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## 3.3. Đọc và ghi file bằng Files

Đây là cách ngắn gọn, rất hữu ích trong các bài tập Java.

java

```java
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("data.txt");

        // Ghi file
        List<String> lines = List.of(
            "Nguyen Van A",
            "Tran Van B",
            "Le Van C"
        );

        Files.write(path, lines);

        // Đọc toàn bộ file
        List<String> result = Files.readAllLines(path);

        for (String line : result) {
            System.out.println(line);
        }
    }
}
```

Một số phương thức quan trọng:

| Phương thức | Chức năng |
| --- | --- |
| `Files.exists(path)` | Kiểm tra tồn tại |
| `Files.createFile(path)` | Tạo file |
| `Files.createDirectories(path)` | Tạo thư mục |
| `Files.delete(path)` | Xóa file |
| `Files.copy(a, b)` | Sao chép |
| `Files.move(a, b)` | Di chuyển |
| `Files.readAllLines(path)` | Đọc toàn bộ dòng |
| `Files.write(path, lines)` | Ghi danh sách dòng |
| `Files.size(path)` | Lấy kích thước |

# 4. Encoding UTF-8

## 4.1. Encoding là gì?

Encoding là cách chuyển đổi ký tự thành byte để lưu trữ hoặc truyền tải.

Ví dụ:

text

```text
Xin chào Java
Tiếng Việt có dấu
```

Nếu sử dụng encoding không phù hợp, ký tự tiếng Việt có thể bị lỗi:

text

```text
Tiáº¿ng Viá»‡t
```

UTF-8 là encoding phổ biến, hỗ trợ tiếng Việt và nhiều ngôn ngữ khác.

## 4.2. Ghi file với UTF-8

Nên sử dụng `StandardCharsets.UTF_8` để chỉ định encoding rõ ràng.

java

```java
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("vietnamese.txt");

        List<String> lines = List.of(
            "Xin chào Java",
            "Tôi đang học lập trình",
            "Tiếng Việt có dấu"
        );

        Files.write(
            path,
            lines,
            StandardCharsets.UTF_8
        );
    }
}
```

## 4.3. Đọc file với UTF-8

java

```java
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("vietnamese.txt");

        List<String> lines = Files.readAllLines(
            path,
            StandardCharsets.UTF_8
        );

        for (String line : lines) {
            System.out.println(line);
        }
    }
}
```

## 4.4. Ghi nối tiếp vào file (Append)

Mặc định `Files.write()` sẽ thay thế nội dung file nếu file đã tồn tại.

Muốn ghi thêm nội dung vào cuối file:

java

```java
Files.write(
    path,
    List.of("Dong moi"),
    StandardCharsets.UTF_8,
    StandardOpenOption.CREATE,
    StandardOpenOption.APPEND
);
```

Các tùy chọn thường dùng:

| Tùy chọn | Ý nghĩa |
| --- | --- |
| `CREATE` | Tạo file nếu chưa có |
| `APPEND` | Ghi nối tiếp |
| `TRUNCATE_EXISTING` | Xóa nội dung cũ trước khi ghi |
| `CREATE_NEW` | Chỉ tạo file mới, lỗi nếu đã tồn tại |

# 5. Bài tập tổng hợp thực hành

Đề bài: Xây dựng chương trình quản lý sinh viên bằng file.

Yêu cầu:

1. Nhập tên sinh viên.
2. Nhập ngày sinh theo định dạng `yyyy-MM-dd`.
3. Ghi tên và ngày sinh vào file `students.txt`.
4. Sử dụng UTF-8 để hỗ trợ tiếng Việt.
5. Ghi thêm sinh viên mới mà không xóa dữ liệu cũ.
6. Đọc toàn bộ danh sách sinh viên từ file.
7. In ra thời điểm hiện tại theo múi giờ Việt Nam.

Bài tập áp dụng tổng hợp## Code tham khảo

java

```java

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Path path = Paths.get("students.txt");

        try {
            System.out.print("Nhap ten: ");
            String name = sc.nextLine();

            System.out.print("Nhap ngay sinh (yyyy-MM-dd): ");
            LocalDate birthday =
                LocalDate.parse(sc.nextLine());

            // Ghi thêm dữ liệu
            String data = name + "," + birthday;

            Files.write(
                path,
                List.of(data),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );

            System.out.println("\nDanh sach sinh vien:");

            // Đọc dữ liệu
            List<String> lines = Files.readAllLines(
                path,
                StandardCharsets.UTF_8
            );

            for (String line : lines) {
                System.out.println(line);
            }

            // Thời gian hiện tại tại Việt Nam
            ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");

            ZonedDateTime now = ZonedDateTime.now(zone);

            System.out.println("\nThoi gian hien tai: " + now);

        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }

        sc.close();
    }
}
```

# 6. Tổng kết kiến thức cần nhớ

| Chủ đề | Thành phần cần nắm |
| --- | --- |
| Ngày tháng | `LocalDate` |
| Thời điểm UTC | `Instant` |
| Múi giờ | `ZoneId` |
| Ngày giờ có múi giờ | `ZonedDateTime` |
| Đường dẫn | `Path`, `Paths` |
| Thao tác file | `Files` |
| Đọc file | `readAllLines()`, `newBufferedReader()` |
| Ghi file | `write()`, `newBufferedWriter()` |
| Encoding | `StandardCharsets.UTF_8` |
| Ghi nối tiếp | `StandardOpenOption.APPEND` |
| Đóng tài nguyên | `try-with-resources` |

