# Flyweight Pattern — Bank Bildiriş Sistemi

## Pattern haqqında

**Flyweight** structural design pattern-lərindən biridir. Çox sayda oxşar obyekt yaratmaq əvəzinə ortaq hissələri paylaşır — yaddaşı optimallaşdırır.

**Real dünya analoqu:** Mətn redaktoru. "a" hərfini 10,000 dəfə yazdın — 10,000 ayrı obyekt yaratmaq əvəzinə "a" hərfinin bir obyekti var, yalnız mövqeyi (x, y) dəyişir.

---

## Əsas ideya — Intrinsic vs Extrinsic

```
Intrinsic (daxili) — paylaşılır, dəyişmir
    NotificationType: code, label, icon, color

Extrinsic (xarici) — hər instansiya üçün fərqli
    Notification: message, recipient, time
```

Flyweight yalnız **intrinsic** hissəni saxlayır. **Extrinsic** hissə çöldən ötürülür.

---

## Struktur

```
NotificationTypeFactory     ← Flyweight Factory (cache saxlayır)
        │
        └── NotificationType  ← Flyweight (intrinsic — paylaşılır)

Notification                  ← Client (extrinsic saxlayır)
        │ istifadə edir
        └── NotificationType  ← factory-dən alır
```

---

## NotificationType — Flyweight

Paylaşılan obyekt. `final` olmalıdır — dəyişsə bütün istifadəçilərə təsir edir.

```java
public class NotificationType {

    private final String code;
    private final String label;
    private final String icon;
    private final String color;

    public NotificationType(String code, String label, String icon, String color) {
        this.code = code;
        this.label = label;
        this.icon = icon;
        this.color = color;
    }

    public boolean isUrgent() {
        return "SECURITY".equals(code);
    }

    public void render(String message, String recipient, LocalDateTime time) {
        System.out.printf("%s [%s] %s | %s | %s%n",
            icon, label, recipient, message,
            time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
    }
}
```

---

## NotificationTypeFactory — Factory

Cache mexanizmi. Eyni tip bir dəfə yaradılır, sonra cache-dən qaytarılır.

```java
public class NotificationTypeFactory {

    private static final Map<String, NotificationType> cache = new HashMap<>();

    public static NotificationType getType(String code) {
        return cache.computeIfAbsent(code, k -> switch (k) {
            case "TRANSFER" -> new NotificationType(k, "Köçürmə",       "↔️",  "#1A73E8");
            case "PAYMENT"  -> new NotificationType(k, "Ödəniş",        "💳",  "#34A853");
            case "SECURITY" -> new NotificationType(k, "Təhlükəsizlik", "🔐",  "#EA4335");
            case "CAMPAIGN" -> new NotificationType(k, "Kampaniya",     "🎯",  "#FBBC04");
            default -> throw new IllegalArgumentException("Naməlum tip: " + k);
        });
    }

    public static int getCacheSize() {
        return cache.size();
    }
}
```

> `computeIfAbsent` — key varsa cache-dən qaytarır, yoxdursa yaradır və saxlayır.

---

## Notification — Client

Extrinsic məlumatları saxlayır. `NotificationType`-i factory-dən alır.

```java
public class Notification {

    private final NotificationType type;
    private final String message;
    private final String recipient;
    private final LocalDateTime time;

    public Notification(String typeCode, String message, String recipient, LocalDateTime time) {
        this.type = NotificationTypeFactory.getType(typeCode);
        this.message = message;
        this.recipient = recipient;
        this.time = time;
    }

    public void display() {
        if (type.isUrgent()) System.out.print("❗ ");
        type.render(message, recipient, time);
    }
}
```

---

## MainService

```java
public class MainService {

    public static void main(String[] args) {
        List<Notification> notifications = new ArrayList<>();

        for (int i = 0; i < 1_000; i++) {
            notifications.add(new Notification("TRANSFER", "500 AZN köçürüldü",   "ali@example.com", LocalDateTime.now()));
            notifications.add(new Notification("PAYMENT",  "120 AZN ödənildi",    "ali@example.com", LocalDateTime.now()));
            notifications.add(new Notification("SECURITY", "Yeni cihazdan giriş", "ali@example.com", LocalDateTime.now()));
            notifications.add(new Notification("CAMPAIGN", "50% endirim!",         "ali@example.com", LocalDateTime.now()));
        }

        System.out.println("Cache ölçüsü: " + NotificationTypeFactory.getCacheSize()); // → 4

        notifications.forEach(Notification::display);
    }
}
```

**Çıxış (ilk 4 sətir):**
```
↔️  [Köçürmə]       ali@example.com | 500 AZN köçürüldü   | 23.05.2026 14:35
💳 [Ödəniş]        ali@example.com | 120 AZN ödənildi    | 23.05.2026 14:35
❗ 🔐 [Təhlükəsizlik] ali@example.com | Yeni cihazdan giriş | 23.05.2026 14:35
🎯 [Kampaniya]     ali@example.com | 50% endirim!         | 23.05.2026 14:35
```

---

## Yaddaş müqayisəsi

```
Flyweight olmadan: 4,000 NotificationType obyekti
Flyweight ilə:             4 NotificationType obyekti  ← 1,000x az
```

4 tip nə qədər bildiriş olursa olsun — cache həmişə 4 olaraq qalır.

---

## Java-da real nümunələr

```java
// String Pool — ən məşhur Flyweight
String a = "hello";
String b = "hello";
System.out.println(a == b); // → true — eyni obyekt

// Integer cache (-128 to 127)
Integer x = 127;
Integer y = 127;
System.out.println(x == y); // → true — cache-dən gəlir

Integer p = 128;
Integer q = 128;
System.out.println(p == q); // → false — cache xaricindədir
```

---

## Nə vaxt istifadə etmək lazımdır

- Çox sayda oxşar obyekt yaradılırsa
- Obyektlərin böyük hissəsi dəyişməyən ortaq məlumat saxlayırsa
- Yaddaş optimallaşdırması kritikdirsə (log sistemi, UI rendering, oyunlar)