# Java Collections Framework

Tài liệu tổng hợp các keyword:

-   `List`, `Set`, `Map`, `Queue`
-   `ArrayList` vs `LinkedList`
-   `HashMap` vs `TreeMap`
-   `equals()` / `hashCode()`
-   `Comparable` / `Comparator`

------------------------------------------------------------------------

## 1. Tổng quan Java Collections Framework

Java Collections Framework (JCF) là tập hợp các interface và class hỗ
trợ lưu trữ, truy xuất, tìm kiếm, sắp xếp và xử lý nhóm đối tượng.

### Cấu trúc tổng quan

``` text
Iterable
└── Collection
    ├── List
    │   ├── ArrayList
    │   ├── LinkedList
    │   └── Vector
    ├── Set
    │   ├── HashSet
    │   ├── LinkedHashSet
    │   └── TreeSet
    └── Queue
        ├── PriorityQueue
        └── Deque
            ├── ArrayDeque
            └── LinkedList

Map (không kế thừa Collection)
├── HashMap
├── LinkedHashMap
├── TreeMap
└── Hashtable
```

**Lưu ý:** `Map` không kế thừa `Collection` vì nó lưu dữ liệu theo cặp
`key-value`, trong khi `Collection` đại diện cho một nhóm phần tử đơn
lẻ.

### Phân biệt nhanh

  -----------------------------------------------------------------------
  Interface         Cách lưu trữ      Đặc điểm chính    Ví dụ
  ----------------- ----------------- ----------------- -----------------
  `List`            Các phần tử theo  Có thứ tự, cho    Danh sách sản
                    vị trí            phép trùng lặp    phẩm

  `Set`             Các phần tử duy   Không cho phép    Tập mã sản phẩm
                    nhất              phần tử trùng     
                                      theo quy tắc      
                                      equality          

  `Map`             Cặp key-value     Key duy nhất,     Tra cứu sản phẩm
                                      value có thể      theo ID
                                      trùng             

  `Queue`           Hàng đợi          Thường xử lý theo Hàng đợi công
                                      FIFO              việc
  -----------------------------------------------------------------------

------------------------------------------------------------------------

## 2. List Interface

`List` là interface đại diện cho danh sách có thứ tự
(insertion/positional order tùy implementation), cho phép truy cập phần
tử thông qua index và cho phép phần tử trùng lặp.

**Các implementation phổ biến:**

-   `ArrayList`: sử dụng mảng động.
-   `LinkedList`: sử dụng danh sách liên kết đôi.
-   `Vector`: mảng động có đồng bộ hóa; thường ít được ưu tiên trong
    code mới.

**Một số phương thức thường dùng:**

  Method                  Ý nghĩa
  ----------------------- ---------------------------------------
  `add(element)`          Thêm phần tử vào cuối danh sách
  `add(index, element)`   Thêm phần tử tại vị trí index
  `get(index)`            Lấy phần tử tại index
  `set(index, element)`   Thay thế phần tử tại index
  `remove(index)`         Xóa phần tử tại index
  `remove(object)`        Xóa lần xuất hiện đầu tiên của object
  `contains(object)`      Kiểm tra phần tử có tồn tại
  `indexOf(object)`       Tìm index đầu tiên
  `size()`                Số lượng phần tử
  `isEmpty()`             Kiểm tra danh sách rỗng

**Ví dụ:**

``` java
List<String> names = new ArrayList<>();

names.add("An");
names.add("Binh");
names.add("An"); // List cho phép trùng lặp

System.out.println(names.get(0)); // An
System.out.println(names.size()); // 3
```

------------------------------------------------------------------------

## 3. ArrayList (implements List Interface)

`ArrayList` là implementation của `List`, sử dụng một mảng nội bộ có khả
năng tự tăng kích thước khi cần.

### Có 3 constructor phổ biến

1.  `ArrayList()`: Tạo danh sách rỗng. Trong các phiên bản Java hiện
    đại, mảng nội bộ được cấp phát khi cần; dung lượng mặc định thường
    được mở rộng đến 10 khi thêm phần tử đầu tiên.
2.  `ArrayList(int initialCapacity)`: Tạo danh sách rỗng với dung lượng
    ban đầu được đặt trước. Hữu ích khi ước lượng được số lượng phần tử
    để giảm số lần mở rộng mảng.
3.  `ArrayList(Collection<? extends E> c)`: Tạo danh sách chứa các phần
    tử được lấy từ collection truyền vào.

### Ưu điểm

-   **Truy xuất theo index nhanh:** `get(index)` có độ phức tạp O(1), vì
    phần tử được lưu trong mảng.
-   **Kích thước linh hoạt:** Tự động mở rộng mảng nội bộ khi số phần tử
    vượt quá dung lượng hiện tại.
-   **Dễ sử dụng:** Có nhiều phương thức tiện ích như `add()`,
    `remove()`, `contains()`, `sort()`.
-   **Tiết kiệm bộ nhớ hơn LinkedList trong nhiều trường hợp:** Không
    cần lưu thêm hai liên kết `next` và `prev` cho từng phần tử.

### Nhược điểm

-   **Thêm/xóa ở đầu hoặc giữa có thể chậm:** Phải dịch chuyển các phần
    tử phía sau, độ phức tạp O(n).
-   **Mở rộng mảng có chi phí:** Khi hết dung lượng, cần tạo mảng lớn
    hơn và sao chép dữ liệu.
-   **Không thread-safe:** Nhiều luồng cùng sửa đổi một `ArrayList` mà
    không có cơ chế đồng bộ có thể gây lỗi hoặc dữ liệu không nhất quán.

### Độ phức tạp thường gặp

  Thao tác                           Độ phức tạp
  ----------------------------- ----------------
  `get(index)` / `set(index)`               O(1)
  `add(element)` vào cuối         O(1) amortized
  `add(index, element)`                     O(n)
  `remove(index)`                           O(n)
  `contains(element)`                       O(n)
  `indexOf(element)`                        O(n)

`O(1) amortized` nghĩa là phần lớn lần thêm cuối danh sách có chi phí
O(1); đôi khi việc mở rộng mảng tốn O(n), nhưng tính trung bình trên
nhiều lần thêm thì chi phí mỗi lần vẫn là O(1).

------------------------------------------------------------------------

## 4. LinkedList (implements List và Deque)

`LinkedList` sử dụng **danh sách liên kết đôi (doubly linked list)**.
Mỗi node lưu dữ liệu và tham chiếu đến node trước (`prev`) và node sau
(`next`).

``` text
null <- [A] <-> [B] <-> [C] -> null
         head              tail
```

### Có 2 constructor phổ biến

1.  `LinkedList()`: Tạo danh sách rỗng.
2.  `LinkedList(Collection<? extends E> c)`: Tạo danh sách chứa các phần
    tử từ collection truyền vào.

### Ưu điểm

-   **Thêm/xóa ở đầu hoặc cuối nhanh:** Thường O(1), vì chỉ cần cập nhật
    các liên kết.
-   **Không cần dịch chuyển hàng loạt phần tử:** Khi chèn/xóa node, chủ
    yếu cập nhật tham chiếu giữa các node.
-   **Hỗ trợ cả List và Deque:** Có thể sử dụng như danh sách hoặc hàng
    đợi/hàng đợi hai đầu.

### Nhược điểm

-   **Truy cập theo index chậm:** `get(index)` là O(n), vì phải duyệt từ
    đầu hoặc cuối đến vị trí cần tìm.
-   **Tốn bộ nhớ hơn ArrayList:** Mỗi node cần lưu dữ liệu và hai tham
    chiếu `prev`, `next`.
-   **Chèn/xóa tại vị trí bất kỳ không mặc nhiên là O(1):** Phải tìm đến
    vị trí trước, mất O(n). Chỉ thao tác liên kết sau khi đã có node/vị
    trí phù hợp mới là O(1).

### Độ phức tạp thường gặp

  Thao tác                             Độ phức tạp
  ---------------------------------- -------------
  `get(index)` / `set(index)`                 O(n)
  `addFirst()` / `addLast()`                  O(1)
  `removeFirst()` / `removeLast()`            O(1)
  `add(index, element)`                       O(n)
  `remove(index)`                             O(n)
  `contains(element)`                         O(n)

### ArrayList vs LinkedList

  -----------------------------------------------------------------------
  Tiêu chí                ArrayList               LinkedList
  ----------------------- ----------------------- -----------------------
  Cấu trúc                Mảng động               Danh sách liên kết đôi

  Truy cập theo index     O(1)                    O(n)

  Thêm cuối               O(1) amortized          O(1)

  Thêm/xóa đầu            O(n)                    O(1)

  Thêm/xóa giữa           O(n), do dịch chuyển    O(n) tìm vị trí; O(1)
                                                  cập nhật liên kết

  Bộ nhớ                  Thường ít hơn           Thường nhiều hơn

  Cache locality          Tốt hơn                 Kém hơn

  Trường hợp phù hợp      Đọc/truy cập theo index Thao tác ở hai đầu
                          thường xuyên            thường xuyên
  -----------------------------------------------------------------------

**Kết luận:** Mặc định nên ưu tiên `ArrayList` cho phần lớn nhu cầu danh
sách. Chọn `LinkedList` khi thực sự cần thao tác thường xuyên ở hai đầu
hoặc cần API `Deque`; không nên chọn chỉ vì nghĩ rằng chèn/xóa giữa luôn
nhanh hơn.

------------------------------------------------------------------------

## 5. Set Interface

`Set` là interface đại diện cho tập hợp **không chứa phần tử trùng
lặp**. Việc xác định hai phần tử có trùng nhau hay không phụ thuộc vào
implementation và quy tắc equality.

### Các implementation phổ biến

-   `HashSet`: Dựa trên hash table, không đảm bảo thứ tự duyệt.
-   `LinkedHashSet`: Duy trì thứ tự thêm phần tử.
-   `TreeSet`: Lưu phần tử theo thứ tự sắp xếp tự nhiên hoặc
    `Comparator`.

### Một số phương thức thường dùng

  Method                Ý nghĩa
  --------------------- -----------------------------------------------------
  `add(element)`        Thêm phần tử; trả về `false` nếu phần tử đã tồn tại
  `remove(element)`     Xóa phần tử
  `contains(element)`   Kiểm tra phần tử
  `size()`              Số lượng phần tử
  `isEmpty()`           Kiểm tra rỗng
  `clear()`             Xóa toàn bộ
  `iterator()`          Lấy iterator để duyệt

**Ví dụ:**

``` java
Set<String> emails = new HashSet<>();

emails.add("a@gmail.com");
emails.add("b@gmail.com");
emails.add("a@gmail.com");

System.out.println(emails.size()); // 2
```

### Lưu ý

-   `HashSet` không đảm bảo thứ tự phần tử khi duyệt.
-   `LinkedHashSet` giữ thứ tự insertion.
-   `TreeSet` sắp xếp phần tử; thao tác cơ bản thường O(log n).
-   Với object tự định nghĩa, cần triển khai đúng `equals()` và
    `hashCode()` để `HashSet` nhận diện phần tử trùng như mong muốn.

------------------------------------------------------------------------

## 6. Map Interface

`Map` lưu dữ liệu theo cặp **key-value**.

-   Mỗi `key` là duy nhất trong map.
-   Nhiều key có thể trỏ đến cùng một value.
-   Khi thêm một key đã tồn tại, value cũ sẽ được thay thế.
-   `Map` không kế thừa `Collection`.

### Các implementation phổ biến

-   `HashMap`: Tra cứu theo hash, không đảm bảo thứ tự.
-   `LinkedHashMap`: Duy trì thứ tự insertion (hoặc access order nếu cấu
    hình).
-   `TreeMap`: Sắp xếp entry theo key.

### Một số phương thức thường dùng

  Method                              Ý nghĩa
  ----------------------------------- -----------------------------------
  `put(key, value)`                   Thêm hoặc cập nhật value theo key
  `get(key)`                          Lấy value theo key
  `getOrDefault(key, defaultValue)`   Lấy value hoặc giá trị mặc định
  `containsKey(key)`                  Kiểm tra key
  `containsValue(value)`              Kiểm tra value
  `remove(key)`                       Xóa entry theo key
  `keySet()`                          Lấy tập hợp các key
  `values()`                          Lấy collection các value
  `entrySet()`                        Lấy tập hợp các cặp key-value
  `putIfAbsent(key, value)`           Chỉ thêm nếu key chưa có mapping
  `size()`                            Số lượng entry

**Ví dụ:**

``` java
Map<String, Integer> stockByProductId = new HashMap<>();

stockByProductId.put("P001", 100);
stockByProductId.put("P002", 50);
stockByProductId.put("P001", 120); // Cập nhật value của P001

System.out.println(stockByProductId.get("P001")); // 120

for (Map.Entry<String, Integer> entry : stockByProductId.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

------------------------------------------------------------------------

## 7. HashMap vs TreeMap

### HashMap

`HashMap` lưu dữ liệu bằng cơ chế hash table. Map sử dụng `hashCode()`
để xác định bucket, sau đó sử dụng `equals()` để xác định key có bằng
nhau hay không.

**Ưu điểm**

-   `put()`, `get()`, `remove()` thường có độ phức tạp trung bình O(1).
-   Phù hợp cho bài toán tra cứu nhanh theo key.
-   Cho phép một key `null` và nhiều value `null`.

**Nhược điểm**

-   Không đảm bảo thứ tự key khi duyệt.
-   Hiệu năng phụ thuộc vào chất lượng `hashCode()` và cách phân bố
    hash.
-   Không tự động sắp xếp key.

### TreeMap

`TreeMap` lưu key theo thứ tự, thường được triển khai bằng cây đỏ-đen
(Red-Black Tree).

**Ưu điểm**

-   Key luôn được duy trì theo thứ tự tự nhiên hoặc `Comparator` được
    cung cấp.
-   `firstKey()`, `lastKey()`, `higherKey()`, `lowerKey()` hỗ trợ truy
    vấn theo thứ tự.
-   Phù hợp khi cần duyệt dữ liệu đã sắp xếp hoặc truy vấn theo khoảng
    key.

**Nhược điểm**

-   `put()`, `get()`, `remove()` thường O(log n), chậm hơn HashMap trong
    tra cứu thông thường.
-   Key cần so sánh được với nhau bằng natural ordering hoặc
    `Comparator`.
-   Không chấp nhận key `null` với natural ordering; comparator tùy
    chỉnh có thể có quy tắc riêng.

### Bảng so sánh

  -----------------------------------------------------------------------
  Tiêu chí                HashMap                 TreeMap
  ----------------------- ----------------------- -----------------------
  Cấu trúc                Hash table              Cây đỏ-đen

  Thứ tự key              Không đảm bảo           Có sắp xếp

  `put()` / `get()` /     Trung bình O(1)         O(log n)
  `remove()`                                      

  Key `null`              Cho phép một key null   Không cho phép với
                                                  natural ordering

  Điều kiện key           Cần `equals()` /        Cần so sánh được
                          `hashCode()` đúng       

  Phù hợp                 Tra cứu nhanh theo key  Dữ liệu cần sắp xếp,
                                                  truy vấn theo khoảng
  -----------------------------------------------------------------------

**Ví dụ:**

``` java
Map<Integer, String> hashMap = new HashMap<>();
hashMap.put(3, "C");
hashMap.put(1, "A");
hashMap.put(2, "B");

Map<Integer, String> treeMap = new TreeMap<>();
treeMap.put(3, "C");
treeMap.put(1, "A");
treeMap.put(2, "B");

System.out.println(treeMap); // Các key được duyệt theo thứ tự 1, 2, 3
```

------------------------------------------------------------------------

## 8. Queue Interface

`Queue` đại diện cho cấu trúc hàng đợi. Quy tắc phổ biến là **FIFO
(First In, First Out)**: phần tử vào trước được xử lý trước.

Tuy nhiên, không phải mọi implementation của Queue đều FIFO tuyệt đối.
Ví dụ `PriorityQueue` lấy phần tử theo độ ưu tiên.

### Hai nhóm phương thức quan trọng

  -----------------------------------------------------------------------
  Thao tác                Ném exception khi thất  Trả về giá trị đặc biệt
                          bại                     khi thất bại
  ----------------------- ----------------------- -----------------------
  Thêm phần tử            `add(e)`                `offer(e)`

  Lấy và xóa đầu hàng đợi `remove()`              `poll()`

  Xem đầu hàng đợi, không `element()`             `peek()`
  xóa                                             
  -----------------------------------------------------------------------

-   `add()` / `offer()`: Thêm phần tử vào queue.
-   `remove()` / `poll()`: Lấy và xóa phần tử ở đầu queue.
-   `element()` / `peek()`: Xem phần tử đầu queue nhưng không xóa.
-   `poll()` và `peek()` trả về `null` nếu queue rỗng; các method
    `remove()` và `element()` ném `NoSuchElementException`.

### Các implementation phổ biến

-   `LinkedList`: Có thể sử dụng như Queue hoặc Deque.
-   `ArrayDeque`: Deque dựa trên mảng động; thường là lựa chọn tốt cho
    queue/stack trong một luồng.
-   `PriorityQueue`: Lấy phần tử có độ ưu tiên cao nhất theo natural
    ordering hoặc comparator; không đảm bảo thứ tự duyệt toàn bộ phần
    tử.

**Ví dụ FIFO với ArrayDeque:**

``` java
Queue<String> tasks = new ArrayDeque<>();

tasks.offer("Task A");
tasks.offer("Task B");
tasks.offer("Task C");

System.out.println(tasks.peek()); // Task A
System.out.println(tasks.poll()); // Lấy và xóa Task A
System.out.println(tasks.poll()); // Task B
```

### Deque

`Deque` (Double Ended Queue) là hàng đợi hai đầu, cho phép thêm/xóa ở cả
đầu và cuối.

Một số method:

-   `addFirst()` / `offerFirst()`
-   `addLast()` / `offerLast()`
-   `removeFirst()` / `pollFirst()`
-   `removeLast()` / `pollLast()`
-   `peekFirst()` / `peekLast()`

Có thể dùng `ArrayDeque` làm stack với `push()`, `pop()`, `peek()`.

------------------------------------------------------------------------

## 9. equals() và hashCode()

Hai method này được định nghĩa trong class `Object`. Chúng rất quan
trọng khi sử dụng object làm phần tử trong `HashSet` hoặc key của
`HashMap`.

### equals()

`equals()` dùng để xác định hai object có được xem là bằng nhau về mặt
logic hay không.

Mặc định, `Object.equals()` so sánh identity (về cơ bản tương đương
`==`), không tự động so sánh các field bên trong object.

Có thể override để định nghĩa equality theo dữ liệu nghiệp vụ.

### hashCode()

`hashCode()` trả về một giá trị kiểu `int`, được sử dụng bởi các cấu
trúc dựa trên hash như `HashMap` và `HashSet` để phân nhóm object vào
bucket.

### Contract giữa equals() và hashCode()

1.  Nếu `a.equals(b) == true` thì bắt buộc
    `a.hashCode() == b.hashCode()`.
2.  Nếu `a.hashCode() == b.hashCode()` thì **không bắt buộc**
    `a.equals(b) == true` (có thể xảy ra hash collision).
3.  Nếu các thông tin được dùng trong `equals()` không thay đổi, kết quả
    `hashCode()` phải nhất quán trong cùng một lần thực thi chương
    trình.
4.  Khi override `equals()`, thông thường phải override cả `hashCode()`.

### Ví dụ

``` java
import java.util.Objects;

class Product {
    private final String id;
    private final String name;

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```

Trong ví dụ này, hai `Product` có cùng `id` được xem là bằng nhau, không
phụ thuộc `name`.

``` java
Set<Product> products = new HashSet<>();

products.add(new Product("P001", "Keyboard"));
products.add(new Product("P001", "Mechanical Keyboard"));

System.out.println(products.size()); // 1
```

### Tại sao HashMap cần cả hai method?

Quy trình đơn giản hóa:

``` text
put(key, value) hoặc get(key)
          |
          v
     key.hashCode()
          |
          v
 Xác định bucket cần tìm
          |
          v
So sánh key bằng equals()
          |
     +----+----+
     |         |
   Bằng      Không bằng
     |         |
  Cập nhật   Tiếp tục tìm
  / trả về   trong bucket
```

**Lỗi thường gặp:** Override `equals()` nhưng không override
`hashCode()`. Hai object có thể được xem là bằng nhau bởi `equals()`
nhưng lại có hash code khác nhau, khiến `HashSet` có thể chứa cả hai
hoặc `HashMap` không tìm thấy key như mong đợi.

**Lưu ý quan trọng:** Không nên thay đổi field tham gia vào
`equals()`/`hashCode()` trong lúc object đang được dùng làm key của
`HashMap` hoặc phần tử của `HashSet`. Việc thay đổi có thể khiến object
nằm ở bucket cũ nhưng được tìm kiếm bằng hash code mới.

------------------------------------------------------------------------

## 10. Comparable và Comparator

Cả `Comparable` và `Comparator` đều dùng để định nghĩa cách so sánh
object, thường phục vụ việc sắp xếp.

### Comparable

`Comparable<T>` định nghĩa **thứ tự tự nhiên (natural ordering)** ngay
bên trong class cần so sánh.

-   Implement interface `Comparable<T>`.
-   Override method `compareTo(T other)`.
-   Thường dùng khi class có một cách sắp xếp mặc định hợp lý.

Quy ước kết quả `compareTo()`:

-   Số âm: object hiện tại đứng trước `other`.
-   Số 0: hai object bằng nhau theo thứ tự so sánh này.
-   Số dương: object hiện tại đứng sau `other`.

**Ví dụ: Sắp xếp Product theo giá tăng dần**

``` java
class Product implements Comparable<Product> {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public int compareTo(Product other) {
        return Double.compare(this.price, other.price);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
```

``` java
List<Product> products = new ArrayList<>();
products.add(new Product("Mouse", 300));
products.add(new Product("Keyboard", 800));
products.add(new Product("Monitor", 500));

Collections.sort(products); // Dùng compareTo()
```

### Comparator

`Comparator<T>` định nghĩa một bộ quy tắc so sánh **tách biệt khỏi class
dữ liệu**.

-   Override method `compare(T a, T b)`, hoặc tạo comparator bằng
    lambda.
-   Có thể tạo nhiều comparator cho nhiều tiêu chí sắp xếp khác nhau.
-   Không cần sửa class gốc.

**Ví dụ: Sắp xếp Product theo tên hoặc giá**

``` java
// Theo tên tăng dần
products.sort(Comparator.comparing(Product::getName));

// Theo giá giảm dần
products.sort(
    Comparator.comparingDouble(Product::getPrice).reversed()
);
```

Có thể kết hợp nhiều tiêu chí:

``` java
products.sort(
    Comparator.comparingDouble(Product::getPrice)
              .thenComparing(Product::getName)
);
```

### Comparable vs Comparator

  ----------------------------------------------------------------------------
  Tiêu chí                Comparable                 Comparator
  ----------------------- -------------------------- -------------------------
  Mục đích                Thứ tự tự nhiên của class  Quy tắc so sánh bên ngoài
                                                     class

  Package                 `java.lang`                `java.util`

  Method                  `compareTo(other)`         `compare(a, b)`

  Vị trí định nghĩa       Trong class dữ liệu        Class riêng, biến, lambda
                                                     hoặc method

  Số quy tắc              Thường một thứ tự tự nhiên Có thể tạo nhiều quy tắc

  Sử dụng                 `Collections.sort(list)`   `list.sort(comparator)`
                          hoặc `list.sort(null)`     
  ----------------------------------------------------------------------------

**Khi nào dùng?**

-   Dùng `Comparable` nếu object có một thứ tự tự nhiên rõ ràng, ví dụ
    số tăng dần, ngày tháng tăng dần.
-   Dùng `Comparator` nếu cần nhiều cách sắp xếp, ví dụ sản phẩm theo
    giá, tên, số lượng tồn kho.
-   Có thể kết hợp cả hai: class định nghĩa `Comparable` mặc định, còn
    các màn hình/nghiệp vụ truyền `Comparator` riêng.

### Comparator với TreeMap và TreeSet

`TreeMap` và `TreeSet` sử dụng thứ tự so sánh để duy trì cấu trúc đã sắp
xếp.

``` java
Set<String> names = new TreeSet<>(Comparator.reverseOrder());
names.add("An");
names.add("Binh");
names.add("Cuong");

System.out.println(names); // [Cuong, Binh, An]
```

Với `TreeMap`/`TreeSet`, kết quả so sánh bằng 0 được xem là cùng vị
trí/thứ tự khóa, nên phần tử/key mới có thể không được thêm như một phần
tử riêng. Vì vậy, comparator nên nhất quán với `equals()` nếu muốn tránh
hành vi gây bất ngờ.

------------------------------------------------------------------------

## 11. Bảng tổng hợp độ phức tạp

Các độ phức tạp dưới đây là mức thường gặp, với giả định triển khai và
dữ liệu thông thường. Không phải cam kết thời gian tuyệt đối cho mọi
trường hợp.

  --------------------------------------------------------------------------------
  Collection      Truy cập / tìm            Thêm             Xóa Ghi chú
                            kiếm                                 
  -------------- --------------- --------------- --------------- -----------------
  `ArrayList`       `get`: O(1),      Cuối: O(1)            O(n) Tốt cho truy cập
                    search: O(n)   amortized; vị                 theo index
                                       trí: O(n)                 

  `LinkedList`              O(n)   Hai đầu: O(1)   Hai đầu: O(1) Theo index vẫn
                                                                 O(n)

  `HashSet`      Trung bình O(1) Trung bình O(1) Trung bình O(1) Phụ thuộc hash

  `TreeSet`             O(log n)        O(log n)        O(log n) Có thứ tự

  `HashMap`         `get`: trung Trung bình O(1) Trung bình O(1) Key cần
                       bình O(1)                                 equals/hashCode

  `TreeMap`             O(log n)        O(log n)        O(log n) Key được sắp xếp

  `ArrayDeque`     Đầu/cuối O(1)   Đầu/cuối O(1)   Đầu/cuối O(1) Queue/stack hiệu
                                       amortized                 quả
  --------------------------------------------------------------------------------

------------------------------------------------------------------------

## 12. Chọn Collection nào trong thực tế?

  -----------------------------------------------------------------------
  Nhu cầu                             Nên cân nhắc
  ----------------------------------- -----------------------------------
  Danh sách sản phẩm, cần truy cập    `ArrayList`
  theo index                          

  Danh sách cần thêm/xóa thường xuyên `ArrayDeque` hoặc `LinkedList`
  ở hai đầu                           

  Loại bỏ phần tử trùng lặp, không    `HashSet`
  cần thứ tự                          

  Loại bỏ trùng lặp nhưng giữ thứ tự  `LinkedHashSet`
  thêm                                

  Tập phần tử duy nhất và cần sắp xếp `TreeSet`

  Tra cứu nhanh sản phẩm theo ID      `HashMap`

  Tra cứu theo ID và cần key có thứ   `TreeMap`
  tự                                  

  Xử lý tác vụ theo FIFO              `ArrayDeque`

  Xử lý tác vụ theo độ ưu tiên        `PriorityQueue`
  -----------------------------------------------------------------------

### Ví dụ gắn với bài toán quản lý kho

``` java
// Danh sách sản phẩm để hiển thị
List<Product> products = new ArrayList<>();

// Tra cứu sản phẩm nhanh theo ID
Map<String, Product> productById = new HashMap<>();

// Lưu các mã sản phẩm không trùng
Set<String> productIds = new HashSet<>();

// Hàng đợi các tác vụ cần xử lý lần lượt
Queue<String> pendingTasks = new ArrayDeque<>();
```

Một collection không nhất thiết thay thế collection khác. Trong một ứng
dụng, có thể dùng đồng thời `List` để hiển thị danh sách, `Map` để tra
cứu nhanh và `Set` để kiểm tra trùng lặp.

------------------------------------------------------------------------

## 13. Bài tập thực hành

### Bài 1: Quản lý danh sách sản phẩm bằng List

Tạo class `Product` gồm:

-   `id`
-   `name`
-   `price`
-   `quantity`

Yêu cầu:

1.  Thêm sản phẩm vào `ArrayList`.
2.  Tìm sản phẩm theo ID.
3.  Cập nhật giá và số lượng.
4.  Xóa sản phẩm theo ID.
5.  Lọc sản phẩm có giá lớn hơn một giá trị cho trước.
6.  Sắp xếp sản phẩm theo giá tăng dần.

### Bài 2: Loại bỏ dữ liệu trùng bằng Set

1.  Tạo danh sách các mã sản phẩm có phần tử trùng.
2.  Chuyển danh sách sang `HashSet`.
3.  In ra số lượng mã duy nhất.
4.  Thử thay `HashSet` bằng `LinkedHashSet` và quan sát thứ tự.
5.  Tạo class `Product` và override `equals()` / `hashCode()` theo `id`,
    sau đó kiểm tra hai object có cùng ID.

### Bài 3: Quản lý tra cứu bằng Map

1.  Tạo `Map<String, Product>` với key là ID.
2.  Thêm, cập nhật, xóa sản phẩm.
3.  Tìm sản phẩm bằng ID.
4.  Duyệt map bằng `entrySet()`.
5.  Thử dùng `TreeMap` để quan sát thứ tự key.

### Bài 4: Mô phỏng hàng đợi công việc

1.  Tạo `Queue<String>` bằng `ArrayDeque`.
2.  Thêm 5 công việc.
3.  Lấy và xóa từng công việc bằng `poll()`.
4.  Kiểm tra queue rỗng.
5.  Thử `PriorityQueue<Integer>` để quan sát cách lấy phần tử theo thứ
    tự ưu tiên.

### Bài 5: Sắp xếp với Comparable và Comparator

1.  Cho `Product` implement `Comparable<Product>` để sắp xếp theo giá
    tăng dần.
2.  Dùng `Comparator` để sắp xếp theo tên.
3.  Dùng `Comparator` để sắp xếp theo giá giảm dần.
4.  Sắp xếp theo giá tăng dần, nếu giá bằng nhau thì theo tên.

------------------------------------------------------------------------

## 14. Checklist kiến thức cần nắm

-   [ ] Phân biệt `List`, `Set`, `Map`, `Queue`.
-   [ ] Biết khi nào chọn `ArrayList`, khi nào chọn `LinkedList`.
-   [ ] Hiểu vì sao `ArrayList.get(index)` nhanh hơn
    `LinkedList.get(index)`.
-   [ ] Phân biệt `HashSet`, `LinkedHashSet`, `TreeSet`.
-   [ ] Phân biệt `HashMap`, `LinkedHashMap`, `TreeMap`.
-   [ ] Hiểu vai trò của `equals()` và `hashCode()` trong `HashMap` /
    `HashSet`.
-   [ ] Nắm contract: hai object bằng nhau theo `equals()` phải có cùng
    `hashCode()`.
-   [ ] Phân biệt `Comparable.compareTo()` và `Comparator.compare()`.
-   [ ] Biết dùng lambda, `Comparator.comparing()`, `reversed()` và
    `thenComparing()`.
-   [ ] Biết chọn collection phù hợp với yêu cầu nghiệp vụ.
