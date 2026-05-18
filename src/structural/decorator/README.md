# Decorator Pattern — Qiymət Hesablama Sistemi

## Pattern haqqında

**Decorator** structural design pattern-lərindən biridir. Mövcud obyektin kodunu dəyişdirmədən üzərinə yeni davranış əlavə edir.

**Real dünya analoqu:** Qəhvə sifariş edirsən. Əsas qəhvə var — üzərinə süd, üzərinə şokolad əlavə edirsən. Hər əlavə qiyməti dəyişir, amma əsas qəhvə obyekti dəyişmir.

---

## Adapter ilə fərqi

| | Adapter | Decorator |
|---|---|---|
| **Məqsəd** | İnterfeysi dəyişdir | Davranış əlavə et |
| **İnterfeys** | Fərqli interfeysə çevirir | Eyni interfeysi saxlayır |

---

## Struktur

```
PriceCalculator (interface)
        ▲
        ├── BasePriceCalculator       ← Əsas implementasiya
        │
        └── BasePriceDecorator        ← Abstract decorator
                ▲
                ├── DiscountDecorator
                ├── TaxDecorator
                └── ShippingDecorator
```

---

## Siniflərin təsviri

### `PriceCalculator` (interface)
```java
public interface PriceCalculator {
    double calculate(double basePrice);
    String describe();
}
```

### `BasePriceDecorator` (abstract decorator)

Bütün decorator-ların əsasıdır. `wrappee`-ni saxlayır və `super.calculate()` ilə əvvəlki davranışı ötürür.

```java
public abstract class BasePriceDecorator implements PriceCalculator {

    private final PriceCalculator priceCalculator;

    protected BasePriceDecorator(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    @Override
    public double calculate(double basePrice) {
        return priceCalculator.calculate(basePrice);
    }

    @Override
    public String describe() {
        return priceCalculator.describe();
    }
}
```

### Konkret decorator-lar

```java
public class DiscountDecorator extends BasePriceDecorator {

    private final double discountRate;

    public DiscountDecorator(PriceCalculator priceCalculator, double discountRate) {
        super(priceCalculator);
        this.discountRate = discountRate;
    }

    @Override
    public double calculate(double basePrice) {
        return super.calculate(basePrice) * (1 - discountRate / 100);
    }

    @Override
    public String describe() {
        return String.format("%s -> %.0f%% endirim", super.describe(), discountRate);
    }
}
```

`TaxDecorator` və `ShippingDecorator` eyni strukturu izləyir — yalnız əməliyyat fərqlənir (`* 1.18` və `+ sabit məbləğ`).

---

## İstifadəsi

```java
PriceCalculator base     = new BasePriceCalculator();
PriceCalculator discount = new DiscountDecorator(base, 10);
PriceCalculator tax      = new TaxDecorator(discount, 18);
PriceCalculator shipping = new ShippingDecorator(tax, 5.0);

new CheckoutPriceService(shipping).printSummary(100.0);
```

**Çıxış:**
```
Əməliyyatlar : Base Price -> 10% endirim -> 18% vergi -> +5.0 çatdırılma
Əsas qiymət  : 100.00 AZN
Yekun qiymət : 111.20 AZN
```

---

## Java standart kitabxanasındakı nümunələr

```java
// java.io tamamilə Decorator pattern üzərindədir
InputStream raw      = new FileInputStream("file.txt");
InputStream buffered = new BufferedInputStream(raw);      // decorator
InputStream zipped   = new GZIPInputStream(buffered);     // decorator
```