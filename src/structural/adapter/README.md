# Adapter Pattern — Ödəniş Sistemi

## Pattern haqqında

**Adapter** structural design pattern-lərindən biridir. Uyğunsuz iki interfeys arasında körpü rolu oynayır — mövcud kodu dəyişdirmədən yeni sistemlə işləməsini təmin edir.

**Real dünya analoqu:** Avropadan Amerikaya gedəndə götürdüyün elektrik adapteri. Rozetka dəyişmir, cihazın da dəyişmir — araya adapter girир.

---

## Problem

Legacy `LegacyPaymentSystem` class-ı mövcuddur, lakin yeni e-commerce platforması `PaymentProcessor` interfeysini gözləyir. Bu iki sistem birbaşa uyğun gəlmir:

```
LegacyPaymentSystem          PaymentProcessor (interfeys)
─────────────────────        ──────────────────────────────
makePayment(                 processPayment(
  String cardNumber,    ≠      PaymentRequest request
  double amount              )
)
```

---

## Həll

`LegacyPaymentAdapter` sinfi `PaymentProcessor` interfeysini implement edərək iki sistemi bir-birinə bağlayır.

### Struktur

```
PaymentProcessor (interface)
        ▲
        │ implements
        │
LegacyPaymentAdapter
        │ uses
        │
LegacyPaymentSystem (legacy — dəyişdirilmir)
```

---

## Siniflərin təsviri

### `PaymentRequest` (record)

Ödəniş məlumatlarını saxlayan immutable data class-ı.

```java
public record PaymentRequest(
        String cardToken,
        double totalAmount,
        String currency
) {}
```

| Sahə | Tip | Təsvir |
|---|---|---|
| `cardToken` | `String` | Kartın token-i |
| `totalAmount` | `double` | Ödəniş məbləği |
| `currency` | `String` | Valyuta kodu (USD, EUR, AZN) |

---

### `PaymentProcessor` (interface)

Yeni platformanın gözlədiyi müqavilə.

```java
public interface PaymentProcessor {
    void processPayment(PaymentRequest request);
}
```

---

### `LegacyPaymentSystem` (legacy — dəyişdirilmir)

Köhnə ödəniş sistemi. Yalnız `cardNumber` və `amount` qəbul edir, valyuta çevirmə məntiqi yoxdur.

```java
public class LegacyPaymentSystem {
    public void makePayment(String cardNumber, double amount) {
        System.out.println("Paying $" + amount + " with card " + cardNumber);
    }
}
```

---

### `LegacyPaymentAdapter` (Adapter)

Əsas sinif. `PaymentProcessor` interfeysini implement edir, daxilində `LegacyPaymentSystem`-i istifadə edir.

```java
public class LegacyPaymentAdapter implements PaymentProcessor {

    private final LegacyPaymentSystem legacyPaymentSystem;

    public LegacyPaymentAdapter(LegacyPaymentSystem legacyPaymentSystem) {
        this.legacyPaymentSystem = legacyPaymentSystem;
    }

    @Override
    public void processPayment(PaymentRequest request) {
        double amount = switch (request.currency()) {
            case "EUR" -> request.totalAmount() * 1.08;
            case "AZN" -> request.totalAmount() * 0.59;
            default    -> request.totalAmount();
        };

        legacyPaymentSystem.makePayment(request.cardToken(), amount);
    }
}
```

**Adapter-in etdiyi işlər:**

1. `PaymentRequest` obyektini qəbul edir
2. Valyutanı USD-ə çevirir
3. Çevrilmiş məlumatları `LegacyPaymentSystem.makePayment()` formatına ötürür

**Valyuta çevirmə cədvəli:**

| Giriş | Əməliyyat | Nəticə |
|---|---|---|
| USD | dəyişmir | `amount * 1.0` |
| EUR | artırılır | `amount * 1.08` |
| AZN | azaldılır | `amount * 0.59` |

---

### `CheckoutService`

`PaymentProcessor` interfeysindən istifadə edən servis. Konkret implementasiyanı bilmir.

```java
public class CheckoutService {

    private final PaymentProcessor paymentProcessor;

    public CheckoutService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void checkout(PaymentRequest request) {
        paymentProcessor.processPayment(request);
    }
}
```

> `CheckoutService` yalnız `PaymentProcessor` interfeysini tanıyır. `LegacyPaymentAdapter`-dən, `LegacyPaymentSystem`-dən xəbəri yoxdur. Bu **dependency inversion principle**-in tətbiqidir.

---

## İstifadəsi

```java
// Asılılıqları çölddən inject edirik
LegacyPaymentSystem legacy = new LegacyPaymentSystem();
PaymentProcessor adapter   = new LegacyPaymentAdapter(legacy);
CheckoutService service    = new CheckoutService(adapter);

// USD — dəyişmir
service.checkout(new PaymentRequest("4169738812345678", 100, "USD"));
// → Paying $100.0 with card 4169738812345678

// AZN → USD
service.checkout(new PaymentRequest("4169738812345678", 100, "AZN"));
// → Paying $59.0 with card 4169738812345678

// EUR → USD
service.checkout(new PaymentRequest("4169738812345678", 100, "EUR"));
// → Paying $108.0 with card 4169738812345678
```

---

## Adapter pattern-i nə vaxt istifadə etmək lazımdır

- Legacy sistemlə yeni sistem inteqrasiyası zamanı
- 3rd party library interfeysini öz sisteminə uyğunlaşdıranda
- `implements` edə bilmədiyin class-larla işləyəndə

## Java standart kitabxanasındakı nümunələr

```java
// Arrays.asList() — array-i List interfesinə adapt edir
List<String> list = Arrays.asList("Java", "Python", "Go");

// InputStreamReader — byte stream-i char stream-ə adapt edir
BufferedReader reader = new BufferedReader(
    new InputStreamReader(System.in)
);
```