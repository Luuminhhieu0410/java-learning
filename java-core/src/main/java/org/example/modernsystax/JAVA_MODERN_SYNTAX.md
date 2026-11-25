# Tổng hợp các tính năng Java 9–21

Java từ phiên bản 9 đến 21 đã bổ sung nhiều tính năng giúp code ngắn gọn, dễ đọc, an toàn kiểu dữ liệu và dễ bảo trì hơn.

Dưới đây là các tính năng quan trọng, kèm phiên bản giới thiệu, giải thích và ví dụ thực tế.

## 1. `var` – Local Variable Type Inference (Java 10)

Cho phép khai báo biến cục bộ mà không cần ghi rõ kiểu dữ liệu. Java tự suy luận kiểu dựa trên giá trị khởi tạo.

Trước Java 10:

java

```java
String name = "Java";
int age = 21;
List<String> languages = new ArrayList<>();
```

Từ Java 10:

java

```java
var name = "Java";
var age = 21;
var languages = new ArrayList<String>();
```

Lưu ý:

- Chỉ dùng cho biến cục bộ có giá trị khởi tạo.
- Không dùng cho field, tham số method hoặc kiểu trả về.
- `var` không phải kiểu dữ liệu động như JavaScript.

java

```java
var number = 10;
// number = "Hello"; // Lỗi compile
```

## 2. Text Blocks (Java 15)

Cho phép viết chuỗi nhiều dòng mà không cần nối chuỗi hoặc sử dụng nhiều ký tự escape.

Trước Java 15:

java

```java
String json = "{\n" +
              "  \"name\": \"Java\",\n" +
              "  \"version\": 21\n" +
              "}";
```

Từ Java 15:

java

```java
String json = """
        {
          "name": "Java",
          "version": 21
        }
        """;
```

Ứng dụng:

- Viết JSON, XML.
- Viết SQL query.
- Viết HTML template.
- Viết nội dung nhiều dòng.

Ví dụ SQL:

java

```java
String sql = """
        SELECT *
        FROM users
        WHERE status = 'ACTIVE'
        ORDER BY created_at DESC
        """;
```

## 3. Switch Expression (Java 14)

Cho phép `switch` trả về giá trị trực tiếp, thay vì phải sử dụng nhiều câu lệnh `case`, `break` và biến trung gian.

Trước Java 14:

java

```java
String dayType;

switch (day) {
    case "MONDAY":
    case "TUESDAY":
    case "WEDNESDAY":
    case "THURSDAY":
    case "FRIDAY":
        dayType = "Weekday";
        break;
    case "SATURDAY":
    case "SUNDAY":
        dayType = "Weekend";
        break;
    default:
        dayType = "Unknown";
}
```

Từ Java 14:

java

```java
String dayType = switch (day) {
    case "MONDAY", "TUESDAY", "WEDNESDAY",
         "THURSDAY", "FRIDAY" -> "Weekday";

    case "SATURDAY", "SUNDAY" -> "Weekend";

    default -> "Unknown";
};
```

Có thể sử dụng `yield` nếu cần xử lý nhiều câu lệnh:

java

```java
int result = switch (operation) {
    case "ADD" -> {
        System.out.println("Adding...");
        yield 10 + 20;
    }
    case "SUBTRACT" -> 10 - 20;
    default -> 0;
};
```

Ưu điểm:

- Không cần `break`.
- Tránh lỗi fall-through.
- Có thể trả về giá trị.
- Code dễ đọc hơn.

## 4. Pattern Matching for instanceof (Java 16)

Cho phép kiểm tra kiểu dữ liệu và ép kiểu trong cùng một biểu thức.

Trước Java 16:

java

```java
if (obj instanceof String) {
    String str = (String) obj;
    System.out.println(str.length());
}
```

Từ Java 16:

java

```java
if (obj instanceof String str) {
    System.out.println(str.length());
}
```

Ví dụ thực tế:

java

```java
public void printLength(Object obj) {
    if (obj instanceof String str) {
        System.out.println("Length: " + str.length());
    }
}
```

Ưu điểm:

- Không cần ép kiểu thủ công.
- Giảm code dư thừa.
- Hạn chế lỗi `ClassCastException`.

## 5. Record (Java 16)

Record được sử dụng để tạo các class chuyên lưu trữ dữ liệu (Data Carrier).

Java tự động tạo:

- Constructor.
- Getter dạng accessor.
- `equals()`.
- `hashCode()`.
- `toString()`.

Trước Java 16:

java

```java
public class User {
    private final String name;
    private final int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // equals(), hashCode(), toString()...
}
```

Từ Java 16:

java

```java
public record User(String name, int age) {
}
```

Sử dụng:

java

```java
User user = new User("Nguyen", 25);

System.out.println(user.name());
System.out.println(user.age());
System.out.println(user);
```

Có thể thêm validation:

java

```java
public record User(String name, int age) {
    public User {
        if (age < 0) {
            throw new IllegalArgumentException("Invalid age");
        }
    }
}
```

Lưu ý:

- Record mặc định là immutable đối với các component (field là `final`).
- Không thể kế thừa class khác.
- Có thể implement interface.
- Nếu component chứa object mutable, object đó vẫn có thể bị thay đổi.

## 6. Sealed Classes (Java 17)

Cho phép giới hạn những class nào được phép kế thừa một class hoặc implement một interface.

Trước Java 17:

java

```java
public class Animal {
}

public class Dog extends Animal {
}

public class Cat extends Animal {
}
```

Bất kỳ class nào cũng có thể kế thừa `Animal`.

Từ Java 17:

java

```java
public sealed class Animal
        permits Dog, Cat {
}

public final class Dog extends Animal {
}

public final class Cat extends Animal {
}
```

Có 3 cách khai báo class con:

| Keyword | Ý nghĩa |
| --- | --- |
| `final` | Không cho phép kế thừa tiếp |
| `sealed` | Cho phép một danh sách class cụ thể kế thừa |
| `non-sealed` | Mở lại quyền kế thừa tự do |

Ví dụ:

java

```java
public sealed class Shape
        permits Circle, Rectangle, Triangle {
}

public final class Circle extends Shape {
}

public sealed class Rectangle extends Shape
        permits Square {
}

public final class Square extends Rectangle {
}

public non-sealed class Triangle extends Shape {
}
```

Ứng dụng:

- Thiết kế domain model.
- Kiểm soát hierarchy.
- Xây dựng các loại payment, notification, transaction.
- Kết hợp với pattern matching để xử lý từng loại dữ liệu.

## 7. Pattern Matching for switch (Java 21)

Cho phép sử dụng pattern matching trực tiếp trong `switch`, kiểm tra kiểu dữ liệu và binding biến ngay trong từng `case`.

Trước Java 21:

java

```java
static String process(Object obj) {
    if (obj instanceof String str) {
        return "String: " + str;
    } else if (obj instanceof Integer number) {
        return "Integer: " + number;
    } else if (obj instanceof Double number) {
        return "Double: " + number;
    }

    return "Unknown";
}
```

Java 21:

java

```java
static String process(Object obj) {
    return switch (obj) {
        case String str -> "String: " + str;
        case Integer number -> "Integer: " + number;
        case Double number -> "Double: " + number;
        case null -> "Null";
        default -> "Unknown";
    };
}
```

Có thể kết hợp với `when` để thêm điều kiện:

java

```java
static String check(Object obj) {
    return switch (obj) {
        case Integer n when n > 0 -> "Positive";
        case Integer n when n < 0 -> "Negative";
        case Integer n -> "Zero";
        case String s -> "Text";
        default -> "Unknown";
    };
}
```

## 8. Tổng hợp phiên bản Java

| Tính năng | Phiên bản chính thức |
| --- | --- |
| `var` | Java 10 |
| Text Blocks | Java 15 |
| Switch Expression | Java 14 |
| Pattern Matching instanceof | Java 16 |
| Record | Java 16 |
| Sealed Classes | Java 17 |
| Pattern Matching switch | Java 21 |

Lưu ý: Một số tính năng xuất hiện ở phiên bản Preview trước khi trở thành tính năng chính thức.

## 9. Ví dụ kết hợp các tính năng trong Java 21

Đây là ví dụ giúp hình dung cách các tính năng trên phối hợp với nhau trong một ứng dụng thực tế.

java

```java
sealed interface Payment
        permits Cash, CreditCard, BankTransfer {
}

record Cash(double amount) implements Payment {
}

record CreditCard(double amount, String cardNumber)
        implements Payment {
}

record BankTransfer(double amount, String bank)
        implements Payment {
}

public class PaymentService {

    public static String process(Payment payment) {

        return switch (payment) {
            case Cash c ->
                "Cash payment: " + c.amount();

            case CreditCard c ->
                "Credit card: " + c.amount();

            case BankTransfer b ->
                "Bank transfer: " + b.amount();
        };
    }

    public static void main(String[] args) {
        var payment = new CreditCard(1000, "123456");

        System.out.println(process(payment));
    }
}
```

Ví dụ này kết hợp:

- `sealed interface`: giới hạn các loại Payment.
- `record`: khai báo dữ liệu ngắn gọn.
- `var`: tự suy luận kiểu biến.
- `Pattern Matching switch`: xử lý từng loại Payment mà không cần ép kiểu.
- Exhaustive switch: compiler có thể kiểm tra các trường hợp của sealed hierarchy đã được xử lý đầy đủ.

## 10. Nên ưu tiên học những gì khi nâng cấp lên Java 21?

1. Record – Rất hữu ích khi tạo DTO, request, response.
2. Pattern Matching instanceof – Giảm code ép kiểu.
3. Switch Expression – Code xử lý nghiệp vụ ngắn gọn.
4. Text Blocks – Hữu ích khi viết SQL, JSON.
5. Sealed Classes – Kiểm soát các loại dữ liệu trong domain.
6. Pattern Matching switch – Xử lý nhiều loại object hiệu quả.
7. var – Giúp code ngắn hơn khi kiểu dữ liệu đã rõ ràng.

Điểm quan trọng: Java 21 không chỉ giúp viết ít code hơn mà còn cải thiện khả năng kiểm soát kiểu dữ liệu, tính an toàn và khả năng bảo trì của hệ thống.

