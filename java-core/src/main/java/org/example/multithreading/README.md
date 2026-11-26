1. Thread
2. ExecutorService
3. CompletableFuture
4. synchronized
5. Lock
6. ConcurrentHashMap
7. Virtual Thread (Java 21)

# 1. Trước tiên: Java là gì? Thread là gì?

## 1.1. NULL
## 1.2. Thread là gì?

Hãy tưởng tượng một nhà hàng có một nhân viên.

Nhân viên đó phải:

1. Nhận đơn hàng.
2. Nấu món ăn.
3. Thu tiền.
4. Giao món.

Nếu làm tuần tự, phải xong việc này mới làm việc tiếp theo.

Trong lập trình, cách thực hiện công việc như vậy có thể được gọi là chạy tuần tự (Sequential).

Thread (luồng) là một luồng thực thi công việc trong chương trình.

Hãy tưởng tượng:

- Chương trình Java = Nhà hàng.
- Thread = Nhân viên.
- Task = Công việc cần làm.

Nếu có 1 Thread thì có 1 luồng thực thi công việc.

Nếu có nhiều Thread, nhiều công việc có thể được xử lý đồng thời.


Ví dụ tạo Thread trong Java:

java

```java
public class Main {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println("Thread 1 đang chạy");
        });

        t1.start();

        System.out.println("Main đang chạy");
    }
}
```

Giải thích:

- `new Thread()` tạo một Thread mới.
- `() -> {}` là Lambda Expression, dùng để viết công việc cần chạy.
- `start()` bắt đầu chạy Thread mới.

Chú ý: `start()` khác `run()`.

- `start()`: Tạo luồng thực thi mới.
- `run()`: Gọi phương thức như một lời gọi hàm bình thường, không tự tạo luồng mới.

### Khi nào cần Thread?

Ví dụ ứng dụng cần:

- Tải file.
- Gửi email.
- Xử lý nhiều request cùng lúc.
- Gọi API bên ngoài.

Bạn không muốn một công việc chậm làm toàn bộ chương trình phải chờ.

# 2. ExecutorService – Quản lý Thread

## 2.1. Vấn đề của Thread

Giả sử ứng dụng cần xử lý 1.000 công việc.

Bạn có thể tạo 1.000 Thread:

java

```java
for (int i = 0; i < 1000; i++) {
    new Thread(() -> {
        System.out.println("Processing...");
    }).start();
}
```

Nhưng cách này có vấn đề:

- Tạo quá nhiều Thread.
- Tốn bộ nhớ.
- Tốn tài nguyên CPU.
- Khó quản lý Thread.
- Có thể khiến hệ thống quá tải.

Chúng ta cần một người quản lý nhân viên.

Đó chính là `ExecutorService`.

## 2.2. ExecutorService là gì?

Hãy tưởng tượng:

- Thread = Nhân viên.
- ExecutorService = Người quản lý nhân viên.
- Task = Công việc.

Bạn chỉ cần đưa công việc cho ExecutorService.

Nó sẽ quyết định Thread nào thực hiện công việc.

Company Boss Sitting at Office Desk Giving Tasks to Business Employees Delegating Responsibilities. Leadership, Authority, Effective and Productive Management Concept Cartoon Flat Vector Illustration

Ví dụ:

java

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        ExecutorService executor =
            Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {

            int taskId = i;

            executor.submit(() -> {
                System.out.println(
                    "Task " + taskId +
                    " - " + Thread.currentThread().getName()
                );
            });
        }

        executor.shutdown();
    }
}
```

Giải thích:

- `newFixedThreadPool(3)`: Tạo một nhóm có tối đa 3 Thread.
- `submit()`: Gửi công việc vào nhóm.
- `shutdown()`: Ngừng nhận công việc mới và hoàn thành những công việc đã gửi.

Điểm quan trọng: Có 10 công việc nhưng chỉ tối đa 3 Thread thực thi chúng cùng lúc.

## 2.3. Các loại ExecutorService thường gặp

| Loại | Ý nghĩa |
| --- | --- |
| `newFixedThreadPool(n)` | Nhóm có số Thread cố định |
| `newSingleThreadExecutor()` | Chỉ có một Thread |
| `newCachedThreadPool()` | Tự điều chỉnh số Thread |
| `newScheduledThreadPool(n)` | Chạy công việc theo lịch |

Khi nào dùng?

Khi ứng dụng có nhiều công việc và bạn muốn quản lý, tái sử dụng Thread thay vì tự tạo Thread liên tục.

# 3. CompletableFuture – Xử lý bất đồng bộ

Đây là khái niệm rất quan trọng khi làm Backend Java.

## 3.1. Vấn đề

Giả sử bạn đang xây dựng API lấy thông tin người dùng.

Bạn cần gọi 3 API:

- API 1: Lấy thông tin User.
- API 2: Lấy thông tin Order.
- API 3: Lấy thông tin Payment.

Nếu gọi tuần tự:

Cách 1: Tuần tự (Sequential)

API User – 2 giây

API Order – 3 giây

API Payment – 2 giây

Tổng thời gian khoảng 7 giây.

Nhưng nếu gọi đồng thời:

Cách 2: Bất đồng bộ (Asynchronous)

User

# 2s

Order

# 3s

Payment

# 2s

Tổng thời gian khoảng 3 giây nếu các tác vụ chạy song song và không có thêm thời gian chờ.

Đây là lúc `CompletableFuture` phát huy tác dụng.

## 3.2. CompletableFuture là gì?

Hiểu đơn giản:

CompletableFuture là một đối tượng đại diện cho kết quả của một công việc có thể hoàn thành trong tương lai.

Bạn không cần chờ công việc hoàn thành ngay lập tức.

Ví dụ:

java

```java
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {

        CompletableFuture<String> future =
            CompletableFuture.supplyAsync(() -> {
                return "Hello Java";
            });

        String result = future.join();

        System.out.println(result);
    }
}
```

Kết quả:

text

```text
Hello Java
```

Giải thích:

- `supplyAsync()`: Chạy công việc bất đồng bộ và trả về kết quả.
- `join()`: Chờ kết quả hoàn thành và lấy kết quả.

## 3.3. Kết hợp nhiều CompletableFuture

Ví dụ gọi 3 API đồng thời:

java

```java
CompletableFuture<String> user =
    CompletableFuture.supplyAsync(() -> getUser());

CompletableFuture<String> order =
    CompletableFuture.supplyAsync(() -> getOrder());

CompletableFuture<String> payment =
    CompletableFuture.supplyAsync(() -> getPayment());

CompletableFuture<Void> all =
    CompletableFuture.allOf(user, order, payment);

all.join();

System.out.println(user.join());
System.out.println(order.join());
System.out.println(payment.join());
```

Một số phương thức quan trọng:

| Method | Ý nghĩa |
| --- | --- |
| `supplyAsync()` | Chạy async và trả về kết quả |
| `runAsync()` | Chạy async, không trả về kết quả |
| `thenApply()` | Biến đổi kết quả |
| `thenAccept()` | Nhận kết quả để xử lý |
| `thenCompose()` | Nối các tác vụ async |
| `thenCombine()` | Kết hợp hai kết quả |
| `allOf()` | Chờ nhiều Future hoàn thành |
| `exceptionally()` | Xử lý lỗi |

Ví dụ:

java

```java
CompletableFuture<Integer> future =
    CompletableFuture.supplyAsync(() -> 10)
        .thenApply(number -> number * 2)
        .thenApply(number -> number + 5);

System.out.println(future.join());
```

Kết quả:

text

```text
25
```

# 4. synchronized – Giải quyết vấn đề nhiều Thread cùng truy cập

Đây là một trong những phần quan trọng nhất của Java Concurrency.

## 4.1. Vấn đề Race Condition

Giả sử bạn có một tài khoản ngân hàng với số dư:

java

```java
int balance = 1000;
```

Có 2 Thread cùng thực hiện rút 100 đồng.

Cả hai Thread cùng đọc số dư là 1000 trước khi cập nhật.

Sau đó cả hai cùng ghi kết quả 900.

Kết quả cuối cùng:

text

```text
900
```

Trong khi đáng lẽ phải là:

text

```text
800
```

Đây gọi là Race Condition – nhiều Thread tranh chấp và làm kết quả bị sai.


## 4.2. synchronized là gì?

`synchronized` giúp giới hạn quyền truy cập vào một đoạn code bằng một monitor lock.

Hiểu đơn giản:

Một thời điểm chỉ có một Thread được vào đoạn code được bảo vệ bởi cùng một lock.

Ví dụ:

java

```java
public class BankAccount {

    private int balance = 1000;

    public synchronized void withdraw(int amount) {
        balance = balance - amount;
    }

    public int getBalance() {
        return balance;
    }
}
```

Khi hai Thread cùng gọi `withdraw()` trên cùng một object:

- Thread 1 vào trước.
- Thread 2 phải chờ.
- Thread 1 thực hiện xong.
- Thread 2 mới được vào.

Kết quả được bảo vệ khỏi Race Condition trong tình huống này.

## 4.3. Các cách dùng synchronized

Synchronized method:

java

```java
public synchronized void doSomething() {
    // Code
}
```

Synchronized block:

java

```java
public void doSomething() {

    synchronized (this) {
        // Code
    }
}
```

Khác biệt:

- Method: Khóa toàn bộ method.
- Block: Chỉ khóa đoạn code bên trong block.

Khi nào dùng?

Khi nhiều Thread cùng đọc/ghi dữ liệu dùng chung và bạn cần đảm bảo tính nhất quán.

# 5. Lock – Cơ chế khóa linh hoạt hơn synchronized

## 5.1. Lock là gì?

`Lock` cũng dùng để kiểm soát việc nhiều Thread truy cập dữ liệu chung.

Nó giải quyết vấn đề tương tự `synchronized`, nhưng cung cấp nhiều khả năng điều khiển hơn.

Ví dụ:

java

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private int balance = 1000;

    private final Lock lock = new ReentrantLock();

    public void withdraw(int amount) {

        lock.lock();

        try {
            balance = balance - amount;
        } finally {
            lock.unlock();
        }
    }
}
```

Giải thích:

- `ReentrantLock`: Một loại Lock phổ biến.
- `lock()`: Lấy quyền khóa.
- `unlock()`: Giải phóng khóa.
- `finally`: Đảm bảo giải phóng khóa kể cả khi có exception.

Tại sao phải dùng finally?

Nếu không unlock khi xảy ra lỗi, những Thread khác có thể phải chờ mãi.

## 5.2. synchronized và Lock khác nhau thế nào?

| synchronized | ReentrantLock |
| --- | --- |
| Cú pháp đơn giản | Cần gọi lock/unlock |
| Tự giải phóng lock khi thoát block | Phải tự unlock |
| Không hỗ trợ tryLock | Có tryLock |
| Không hỗ trợ timeout trực tiếp | Có tryLock với timeout |
| Phù hợp khóa cơ bản | Phù hợp khóa phức tạp |

Ví dụ `tryLock()`:

java

```java
if (lock.tryLock()) {
    try {
        System.out.println("Đã lấy được lock");
    } finally {
        lock.unlock();
    }
} else {
    System.out.println("Không lấy được lock");
}
```

Thread thử lấy khóa. Nếu không lấy được ngay thì có thể thực hiện công việc khác.

Khi nào dùng Lock?

Khi bạn cần:

- Thử lấy khóa mà không phải chờ mãi.
- Chờ khóa trong một khoảng thời gian.
- Điều khiển quá trình khóa linh hoạt hơn.

# 6. ConcurrentHashMap – Map an toàn khi nhiều Thread truy cập

## 6.1. Map là gì?

Trong Java, `Map` lưu dữ liệu theo cặp Key và Value.

Ví dụ:

java

```java
Map<String, Integer> scores = new HashMap<>();

scores.put("John", 90);
scores.put("Mary", 95);

System.out.println(scores.get("John"));
```

Kết quả:

text

```text
90
```

## 6.2. Vấn đề với HashMap

Giả sử 100 Thread cùng truy cập một `HashMap`.

Nhiều Thread cùng ghi dữ liệu có thể dẫn đến race condition và dữ liệu không được quản lý an toàn.

`HashMap` không được thiết kế để hỗ trợ nhiều Thread ghi đồng thời một cách an toàn.

## 6.3. ConcurrentHashMap là gì?

`ConcurrentHashMap` là một Map được thiết kế để nhiều Thread có thể truy cập và cập nhật dữ liệu an toàn trong môi trường concurrent.

Ví dụ:

java

```java
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) {

        ConcurrentHashMap<String, Integer> map =
            new ConcurrentHashMap<>();

        map.put("John", 90);
        map.put("Mary", 95);

        System.out.println(map.get("John"));
    }
}
```

Một số method quan trọng:

| Method | Ý nghĩa |
| --- | --- |
| `put()` | Thêm hoặc cập nhật |
| `get()` | Lấy dữ liệu |
| `remove()` | Xóa dữ liệu |
| `putIfAbsent()` | Chỉ thêm nếu key chưa tồn tại |
| `compute()` | Tính toán và cập nhật |
| `merge()` | Kết hợp giá trị |

Ví dụ:

java

```java
ConcurrentHashMap<String, Integer> map =
    new ConcurrentHashMap<>();

map.put("apple", 10);

map.compute("apple", (key, value) -> value + 5);

System.out.println(map.get("apple"));
```

Kết quả:

text

```text
15
```

Lưu ý quan trọng:

`ConcurrentHashMap` không có nghĩa là mọi thao tác ghép nhiều bước đều tự động an toàn.

Ví dụ:

java

```java
if (!map.containsKey("apple")) {
    map.put("apple", 10);
}
```

Hai thao tác trên không phải một thao tác nguyên tử.

Nên dùng:

java

```java
map.putIfAbsent("apple", 10);
```

Khi cần cập nhật nguyên tử, hãy sử dụng các method phù hợp như `compute()`, `merge()`.

# 7. Virtual Thread – Java 21

Java 21 Modern Java

Đây là khái niệm rất đáng học nếu bạn làm Backend Java hiện đại.

## 7.1. Vấn đề của Platform Thread

Trước Java 21, chúng ta thường sử dụng Platform Thread.

Ví dụ:

java

```java
new Thread(() -> {
    callExternalAPI();
}).start();
```

Platform Thread là Thread được JVM ánh xạ tới OS Thread.

Tạo quá nhiều Platform Thread có thể tiêu tốn nhiều tài nguyên.

Đặc biệt với Backend:

- Có hàng nghìn request.
- Mỗi request gọi nhiều API.
- Có nhiều thời gian chờ Database.
- Có nhiều thời gian chờ Network.

Trong lúc chờ, Thread có thể không làm công việc hữu ích nhưng vẫn chiếm tài nguyên.

## 7.2. Virtual Thread là gì?

Virtual Thread là một loại Thread nhẹ được JVM quản lý.

Nó được thiết kế để có thể tạo rất nhiều Thread với chi phí thấp hơn Platform Thread.


Hãy tưởng tượng:

- Platform Thread = Nhân viên toàn thời gian.
- Virtual Thread = Nhân viên công việc nhẹ, được quản lý và phân phối linh hoạt.
- JVM = Người điều phối công việc.

Khi Virtual Thread phải chờ một thao tác I/O phù hợp, JVM thường có thể tạm dừng Virtual Thread đó và dùng Platform Thread để chạy Virtual Thread khác.

Khi công việc chờ hoàn thành, Virtual Thread có thể tiếp tục chạy.

## 7.3. Cách tạo Virtual Thread trong Java 21

Cách 1: Dùng Thread.startVirtualThread()

java

```java
public class Main {

    public static void main(String[] args) {

        Thread.startVirtualThread(() -> {
            System.out.println(
                "Hello from Virtual Thread"
            );
        });
    }
}
```

Cách 2: Dùng ExecutorService

java

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        try (ExecutorService executor =
                 Executors.newVirtualThreadPerTaskExecutor()) {

            for (int i = 0; i < 1000; i++) {

                executor.submit(() -> {
                    callExternalAPI();
                });
            }
        }
    }

    static void callExternalAPI() {
        System.out.println(
            Thread.currentThread()
        );
    }
}
```

Điểm đáng chú ý:

java

```java
Executors.newVirtualThreadPerTaskExecutor()
```

Mỗi task được thực hiện bởi một Virtual Thread mới.

## 7.4. Virtual Thread có thay thế ExecutorService không?

Không.

Hai thứ giải quyết những vấn đề khác nhau:

- ExecutorService: Quản lý và thực thi task.
- Virtual Thread: Cung cấp loại Thread nhẹ hơn để thực thi task.

Bạn hoàn toàn có thể kết hợp chúng:

java

```java
ExecutorService executor =
    Executors.newVirtualThreadPerTaskExecutor();
```

## 7.5. Khi nào nên dùng Virtual Thread?

Phù hợp:

- Backend có nhiều request đồng thời.
- Ứng dụng thường xuyên gọi API.
- Ứng dụng thường xuyên truy vấn Database.
- Các công việc chủ yếu chờ I/O.

Không nên kỳ vọng Virtual Thread giúp tăng tốc các công việc CPU-intensive như:

- Tính toán số học nặng.
- Xử lý ảnh.
- Mã hóa dữ liệu với khối lượng lớn.

Virtual Thread giúp tăng khả năng xử lý đồng thời, không làm CPU mạnh hơn.

# 8. Tổng kết: Phân biệt 7 khái niệm

Đây là bảng bạn nên lưu lại để ôn tập.

| Khái niệm | Giải quyết vấn đề gì? |
| --- | --- |
| Thread | Chạy công việc trên một luồng thực thi |
| ExecutorService | Quản lý và thực thi các task |
| CompletableFuture | Xử lý bất đồng bộ, kết hợp kết quả |
| synchronized | Bảo vệ đoạn code dùng chung |
| Lock | Kiểm soát khóa linh hoạt |
| ConcurrentHashMap | Map an toàn khi nhiều Thread truy cập |
| Virtual Thread | Tạo nhiều luồng nhẹ với chi phí thấp |

## 9. Sơ đồ tư duy: Các khái niệm liên quan với nhau thế nào?

JAVA CONCURRENCY

Xử lý nhiều công việc đồng thời

1. Thực thi

- Thread
- Virtual Thread
- ExecutorService

I Replaced 200 Threads With 10,000. Java Finished 13.5x Faster. - DEV Community

[dev.to](<https://dev.to/tahosin/i-started-10000-java-threads-my-laptop-barely-noticed-2i6g?utm_source=chatgpt.com>)
2. Bất đồng bộ

- CompletableFuture
- thenApply()
- allOf()

3. Đồng bộ dữ liệu

- synchronized
- Lock

4. Dữ liệu đồng thời

- ConcurrentHashMap
- Atomic classes

# 10. Lộ trình học dành cho người mới


## 0/7

[ ]1. Java cơ bảnBiến, kiểu dữ liệu, if/else, loop, method, class, object

[ ]2. Thread và RunnableHiểu cách tạo và chạy Thread

[ ]3. ExecutorServiceHiểu Thread Pool, submit, shutdown

[ ]4. CompletableFutureHiểu async, chaining, kết hợp kết quả

[ ]5. synchronized và LockHiểu Race Condition và Thread Safety

[ ]6. ConcurrentHashMapHiểu dữ liệu dùng chung trong môi trường concurrent

[ ]7. Virtual Thread Java 21Hiểu cách xây dựng ứng dụng nhiều request đồng thời

# 11. Bài thực hành: import 10.000 dòng CSV

Trong package này có một chương trình import chạy được, chia nhỏ theo trách
nhiệm để có thể đọc từng class:

| Class | Vai trò |
| --- | --- |
| `Main` | Đọc tham số, chọn executor và in kết quả |
| `ProductStockCsvReader` | Đọc schema `id,productId,stockId,unitId,count` thành `ProductStock` |
| `ConcurrentInventoryImporter` | Import từng `ProductStock` bằng `CompletableFuture` |
| `ImportResult`, `StockSummary` | Kết quả tổng hợp bất biến |
| `ProductStockRepository` | Nhận các entity sau khi xử lý song song hoàn tất |
| `SynchronizedCounter`, `LockCounter` | Ví dụ bảo vệ dữ liệu dùng chung |
| `ConcurrencyBasicsTest` | Tạo `Thread`, chạy nhiều luồng và kiểm tra counter |

Chạy từ thư mục gốc project:

```bash
mvn -q -DskipTests package
java -cp target/classes org.example.multithreading.Main
```

Mặc định chương trình đọc `data/10000_product_stock.csv`, dùng fixed thread
pool (tối đa 8 worker), tạo `ProductStock` từ mỗi dòng rồi đưa các entity vào
`ProductStockRepository`. Có thể truyền đường dẫn file khác nếu file dùng cùng
header/schema:

```bash
java -cp target/classes org.example.multithreading.Main /path/to/product-stocks.csv
```

File CSV cần header chính xác `id,productId,stockId,unitId,count`; mỗi dòng
chứa ID của `ProductStock`, `Product`, `Stock`, `Unit` và số lượng tồn kho.
Importer giữ các ID tham chiếu này, không tự tạo Product/Stock/Unit vì file
không có tên hay metadata của các entity đó.

Thử executor tạo virtual thread cho từng task:

```bash
java -cp target/classes org.example.multithreading.Main --virtual
```

Virtual thread giúp xử lý nhiều tác vụ thường xuyên chờ I/O; nó không làm phép
tính CPU chạy nhanh hơn và không giới hạn số task. Bài import này chủ yếu xử lý
dữ liệu trong bộ nhớ nên fixed pool là lựa chọn mặc định hợp lý.

## Vì sao phần import không bị race condition?

- Mỗi task chỉ đọc một `ProductStock`; không gọi setter trong lúc import.
- `ConcurrentHashMap.putIfAbsent()` ghi `ProductStock` theo `id` như một thao
  tác nguyên tử, đồng thời từ chối ID trùng.
- `ConcurrentHashMap.compute()` cập nhật `StockSummary` bất biến theo từng
  `stockId`;
  không có thao tác đọc rồi ghi rời rạc trên một `HashMap` dùng chung.
- `CompletableFuture.allOf(...).join()` đợi tất cả task xong trước khi tính tổng
  và trả kết quả. Lỗi trong task được truyền ra thay vì bị bỏ qua.
- `ProductStockRepository` dùng `HashMap` thường, vì vậy chỉ được cập nhật tuần
  tự sau khi các future hoàn tất, không ghi repository trong worker.
- Executor được đóng bằng try-with-resources; task đã gửi hoàn tất trước khi
  chương trình kết thúc.

Chạy ví dụ `Thread`, `synchronized` và `Lock` riêng:

```bash
java -cp target/classes org.example.multithreading.ConcurrencyBasicsTest
```

Hai counter được tăng đồng thời bởi 10 thread. Kết quả mong đợi của cả hai là
`100000`; mỗi lần chạy đều `join()` các thread trước khi đọc kết quả.

## Bài tập

1. Đổi số worker trong `Main` và quan sát thời gian chạy; giải thích vì sao
   tăng thread không luôn làm nhanh hơn.
2. Thay nhóm theo `stockId` bằng nhóm theo `unitId`, nhưng giữ cách cập nhật
   nguyên tử và kết quả bất biến.
3. Thêm một dòng có `id` trùng vào file thử nghiệm và xác nhận import
   báo lỗi thay vì âm thầm ghi đè.
4. Chạy `ConcurrencyBasicsTest` nhiều lần. Sau đó tạm bỏ `synchronized` hoặc
   `lock()`/`unlock()` để quan sát vì sao phép `value++` không phải thao tác
   nguyên tử.

# 12. Cần nhớ gì về thread safety?

## 12.1. `value++` không phải một thao tác duy nhất

Với một biến `int`, `value++` tương đương về ý tưởng với:

1. Đọc giá trị hiện tại.
2. Cộng 1.
3. Ghi giá trị mới.

Nếu hai thread xen kẽ giữa các bước này, một lần tăng có thể bị mất. Đây là
race condition: kết quả phụ thuộc vào thứ tự chạy mà chương trình không kiểm
soát. Việc mỗi thread chạy đúng riêng lẻ không có nghĩa là chương trình chạy
đúng khi chạy đồng thời.

## 12.2. Atomicity, visibility và ordering

Thread safety thường cần cả ba điều:

- **Atomicity (tính nguyên tử):** thao tác quan trọng không bị thread khác chen
  vào giữa. Ví dụ `synchronized` bảo vệ cả vùng đọc-sửa-ghi của counter.
- **Visibility (tính nhìn thấy):** thread này nhìn thấy dữ liệu thread khác đã
  ghi. Thoát một `synchronized` block rồi vào block cùng monitor tạo quan hệ
  happens-before; `Thread.join()` cũng đảm bảo thread gọi `join()` nhìn thấy
  kết quả của thread đã kết thúc.
- **Ordering (thứ tự):** các thay đổi không bị quan sát theo thứ tự gây vi phạm
  logic. Hãy dùng cơ chế đồng bộ của Java thay vì giả định thứ tự thực thi.

Không nên tự bảo vệ bằng `sleep()`, `yield()` hay kiểm tra nhiều lần. Những cách
đó không tạo đảm bảo thread safety.

# 13. Chọn công cụ đồng bộ nào?

| Công cụ | Dùng khi | Lưu ý |
| --- | --- | --- |
| `synchronized` | Bảo vệ một vùng trạng thái nhỏ, cần code rõ ràng | Monitor tự mở khóa khi thoát block; cùng object phải dùng cùng monitor |
| `ReentrantLock` | Cần `tryLock`, timeout hoặc khả năng ngắt lúc đợi khóa | Luôn mở khóa trong `finally`; mọi đường chạy phải nhả khóa |
| `ConcurrentHashMap` | Nhiều thread truy cập map và cập nhật theo key | Một lời gọi atomic không làm cả chuỗi nhiều lời gọi atomic |
| `AtomicInteger` / `LongAdder` | Counter hoặc phép cập nhật số đơn giản | Không phù hợp để bảo vệ invariant liên quan nhiều biến |
| `CompletableFuture` | Kết nối các bước async và xử lý kết quả/lỗi | Không tự làm dữ liệu dùng chung trở nên thread-safe |

`HashMap` không an toàn khi nhiều thread cùng sửa. `ConcurrentHashMap` an toàn
cho các thao tác của map, nhưng nếu value là object mutable thì các thay đổi
trên object đó vẫn cần cơ chế bảo vệ riêng. Trong bài import, `StockSummary` là
record bất biến và được thay thế bằng `compute()` thay vì bị sửa tại chỗ.

Đừng viết chuỗi kiểu `containsKey()` rồi `put()` để thay cho một thao tác nguyên
tử. Dùng `putIfAbsent()`, `compute()` hoặc `merge()` tùy ý nghĩa nghiệp vụ.
Ví dụ importer dùng `putIfAbsent()` để từ chối ID trùng, rồi dùng `compute()`
để cộng summary theo kho.

# 14. ExecutorService và CompletableFuture trong bài import

Luồng xử lý hiện tại:

```text
CSV --đọc/validate tuần tự--> List<ProductStock>
    --mỗi dòng thành một CompletableFuture--> ExecutorService
    --putIfAbsent + compute--> ConcurrentHashMap
    --allOf(...).join()--> ImportResult --> ProductStockRepository (tuần tự)
```

Đọc CSV tuần tự có chủ ý: một `BufferedReader` được dùng bởi một luồng, tránh
đồng bộ hóa việc đọc file không cần thiết. Các dòng được parse thành
`ProductStock` trước, sau đó xử lý đồng thời. Entity có setter nhưng worker chỉ
đọc và không sửa trạng thái trong lúc import.

- `runAsync()` phù hợp với task không cần trả kết quả riêng; kết quả chung được
  gom trong các map concurrent.
- `allOf()` trả về `CompletableFuture<Void>`: nó chỉ báo khi tất cả future hoàn
  tất, không tự gom kết quả từng future.
- `join()` đợi hoàn tất và ném `CompletionException` nếu task thất bại. Không
   bỏ qua lỗi rồi in kết quả thành công giả.
- Dùng `thenApply()` khi biến đổi kết quả ngay sau khi future hoàn thành.
  `thenApplyAsync()` lên lịch thêm một bước async; truyền executor tường minh
  khi cần kiểm soát nơi chạy.
- Không dựa vào common pool mặc định cho mọi workload. Bài import truyền
  executor vào tác vụ để có thể quan sát và đổi loại executor.

Fixed thread pool phù hợp để khống chế số worker cho tác vụ xử lý dữ liệu trong
bộ nhớ. `Executors.newFixedThreadPool()` dùng hàng đợi không giới hạn, nên
không nên mặc định dùng cách này để nhận task vô hạn từ một dịch vụ chịu tải
cao. Với hệ thống production, cần nghĩ thêm về giới hạn hàng đợi, backpressure,
timeout, hủy tác vụ và cách báo lỗi.

# 15. Khi nào dùng virtual thread?

Virtual thread là `Thread`, nhưng JVM quản lý số lượng lớn các thread nhẹ trên
một số platform thread nhỏ hơn. Nó làm code blocking I/O dễ viết mà vẫn phục vụ
nhiều tác vụ chờ cùng lúc. Nó không làm CPU chạy nhanh hơn và không thay thế
executor hay cơ chế đồng bộ.

- Dùng platform thread pool có giới hạn cho công việc CPU-bound; thường bắt đầu
  gần số core CPU rồi đo hiệu năng.
- Có thể dùng virtual thread cho nhiều task blocking I/O; thường tạo một
  virtual thread cho mỗi task thay vì tạo một pool virtual thread.
- Virtual thread không phải giới hạn tài nguyên. Nếu database chỉ cho phép 20
  kết nối, vẫn cần giới hạn số truy cập database, ví dụ bằng semaphore hoặc
  connection pool.
- Bài import cung cấp `--virtual` để so sánh cách chạy. Vì nó chủ yếu xử lý
  trong bộ nhớ, không kỳ vọng virtual thread nhanh hơn fixed pool.

# 16. Đọc code và tự kiểm chứng

1. Đọc `Main`: chọn file đầu vào, chọn executor, đóng executor sau khi import.
2. Đọc `ConcurrentInventoryImporter`: mỗi future xử lý một dòng; xem hai chỗ
   cập nhật map và cách chờ tất cả công việc hoàn tất.
3. Đọc `StockSummary`: record bất biến; `add()` trả về summary mới.
4. Đọc `ConcurrencyBasicsTest`: thread được `start()` rồi `join()`; counter
   chỉ được đọc sau khi mọi worker kết thúc.
5. Đọc `SynchronizedCounter` và `LockCounter`: so sánh vùng khóa ngắn, và vì
   sao `Lock` cần `finally`.

Kiểm tra import đúng bằng cách xác nhận số dòng là `10000`, tổng các số dòng
theo kho cũng là `10000`, và với file mẫu có `count=100` mỗi dòng thì tổng tồn
kho là `1000000`. Chạy lại nhiều lần và với cả hai executor; kết quả nghiệp vụ
phải giống nhau dù thứ tự task thay đổi.

## Bài tập nâng cao

1. Tạo test cho file có header sai, count không hợp lệ và ID trùng; xác nhận
   lỗi được báo rõ ràng.
2. Viết phiên bản xử lý theo batch để không tạo 10.000 future cùng lúc. So sánh
   độ phức tạp và mức dùng bộ nhớ với phiên bản hiện tại.
3. Thêm giới hạn số task được xử lý đồng thời; phân biệt giới hạn số worker với
   giới hạn kết nối đến một dịch vụ bên ngoài.
4. Thử dùng một `HashMap` dùng chung thay cho `ConcurrentHashMap`, chạy nhiều
   lần để thấy lỗi race có thể xuất hiện không ổn định. Không coi việc “chạy
   thử chưa thấy lỗi” là bằng chứng an toàn.

# 17. Các collection và kiểu dữ liệu dùng chung an toàn

Java không có một giải pháp chung cho mọi kiểu dữ liệu. Chọn cấu trúc theo
cách đọc/ghi và invariant cần bảo vệ:

| Dữ liệu | Lựa chọn thường gặp | Khi phù hợp / lưu ý |
| --- | --- | --- |
| `Map` | `ConcurrentHashMap` | Nhiều thread cập nhật theo key; dùng `putIfAbsent`, `compute` hoặc `merge` cho thao tác nguyên tử |
| `Set` | `ConcurrentHashMap.newKeySet()` | Nhiều thread thêm/kiểm tra phần tử; `add()` trả về `false` nếu phần tử đã có |
| `List` | `CopyOnWriteArrayList` | Nhiều đọc, rất ít ghi; mỗi lần ghi sao chép mảng nên không hợp ghi thường xuyên |
| `List` | `Collections.synchronizedList(...)` | Cách bọc list đơn giản; khi duyệt iterator phải `synchronized` trên chính wrapper |
| `Queue` | `ConcurrentLinkedQueue` | Queue concurrent không chặn, không giới hạn; cần tự quản lý áp lực bộ nhớ |
| `Queue` | `ArrayBlockingQueue`, `LinkedBlockingQueue` | Producer-consumer có giới hạn; `put()`/`take()` có thể chờ khi đầy/rỗng |
| `Deque` | `ConcurrentLinkedDeque` | Thêm/lấy ở hai đầu theo cách concurrent |
| Counter | `AtomicInteger`, `AtomicLong` | Cập nhật atomic đơn giản; thao tác nhiều biến vẫn cần khóa hoặc thiết kế khác |
| Bộ đếm tần suất | `LongAdder`, `DoubleAdder` | Nhiều thread cộng thường xuyên; `sum()` trong lúc cập nhật không phải snapshot nguyên tử |
| Tham chiếu có thể thay đổi | `AtomicReference<T>` | Cập nhật/đọc một tham chiếu atomic; không tự làm object mutable bên trong thread-safe |
| Giá trị immutable | `String`, `BigDecimal` | Có thể chia sẻ để đọc; việc gán lại một biến tham chiếu dùng chung vẫn cần visibility |

## Chọn cấu trúc theo bài toán

### List: đọc nhiều hay ghi nhiều?

`CopyOnWriteArrayList` cho phép duyệt trên snapshot ổn định, hữu ích với danh
sách listener/configuration ít thay đổi. Nó tốn chi phí sao chép khi ghi, nên
không phải lựa chọn mặc định cho danh sách import có nhiều thao tác thêm.

```java
List<String> listeners = new CopyOnWriteArrayList<>();
listeners.add("listener-1");
```

`Collections.synchronizedList(new ArrayList<>())` đồng bộ từng lời gọi list.
Một chuỗi nhiều lời gọi vẫn không tự động atomic; iterator cũng cần khóa wrapper:

```java
List<String> names =
        Collections.synchronizedList(new ArrayList<>());

synchronized (names) {
    for (String name : names) {
        System.out.println(name);
    }
}
```

### Set: kiểm tra và thêm trong một thao tác

Khi cần đảm bảo chỉ một thread nhận một email/ID duy nhất, tận dụng kết quả
boolean của `add()` thay vì tách `contains()` và `add()`:

```java
Set<String> importedIds = ConcurrentHashMap.newKeySet();
if (importedIds.add(id)) {
    // Thread này là thread đầu tiên thêm id.
} else {
    // id đã được thêm trước đó.
}
```

### Queue: chọn có giới hạn khi cần backpressure

`ConcurrentLinkedQueue` không block và không giới hạn dung lượng; producer có
thể thêm nhanh hơn worker xử lý, khiến bộ nhớ tăng. `BlockingQueue` có thể giới
hạn dung lượng và phối hợp producer-consumer:

```java
BlockingQueue<String> queue = new ArrayBlockingQueue<>(1_000);
queue.put(csvLine);       // Chờ nếu queue đầy.
String line = queue.take(); // Chờ nếu queue rỗng.
```

Với import lớn, giới hạn queue giúp hệ thống điều tiết tốc độ. Trong code thực
tế cần quyết định rõ cách xử lý khi queue đầy: chờ, từ chối, timeout hay báo lỗi.

### Number: immutable không đồng nghĩa với cập nhật atomic

`Integer`, `Long` và `Double` là immutable, nhưng biểu thức như `counter++` vẫn
là đọc-sửa-ghi trên biến tham chiếu và có thể mất cập nhật. Dùng atomic counter
cho cập nhật đơn:

```java
AtomicInteger counter = new AtomicInteger();
counter.incrementAndGet();
```

`LongAdder`/`DoubleAdder` thường phù hợp cho tổng hợp số lượng có nhiều lần
cộng đồng thời. Nếu cần một giá trị chính xác tức thời để quyết định nghiệp vụ,
chọn cơ chế có invariant phù hợp như `AtomicLong` hoặc `Lock`, không mặc định
thay bằng adder.

### String: object immutable, biến tham chiếu thì không

Nhiều thread có thể đọc cùng một `String` không đổi. Nhưng nếu một thread gán
lại biến dùng chung trong khi thread khác đọc, cần đảm bảo visibility:

```java
AtomicReference<String> currentName =
        new AtomicReference<>("John");
currentName.set("Mary");
```

`AtomicReference` làm thao tác trên tham chiếu atomic; nếu tham chiếu trỏ tới
object mutable, các thay đổi bên trong object vẫn cần được bảo vệ riêng.

## Nguyên tắc áp dụng

- **Immutable** nghĩa là trạng thái object không đổi; không tự đảm bảo mọi cách
  chia sẻ/thay thế tham chiếu đều được publish đúng.
- **Thread-safe collection** bảo vệ các thao tác collection được cung cấp; nó
  không biến một chuỗi nhiều thao tác thành transaction.
- **Atomic operation** là một thao tác không bị xen ngang; ghép nhiều thao tác
  atomic lại không tự tạo thành một thao tác atomic lớn hơn.
- Trước khi chọn collection, xác định ai đọc/ghi, tỷ lệ đọc/ghi, cần giới hạn
  dung lượng không, và invariant nào phải đúng sau mỗi cập nhật.

Trong importer ở bài thực hành, `ConcurrentHashMap` hợp với dữ liệu gom theo
ID/kho, còn `StockSummary` bất biến giúp việc cập nhật theo key rõ ràng. Nếu
chuyển sang mô hình producer-consumer hoặc chia sẻ một list, hãy chọn cấu trúc
phù hợp với workload thay vì mặc định tìm một class “concurrent” cho mọi kiểu.
