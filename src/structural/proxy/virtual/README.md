# Proxy Pattern (Virtual) — Lazy Initialization

## Pattern haqqında

**Proxy** structural design pattern-lərindən biridir. Əsl obyektə birbaşa çıxış əvəzinə araya bir "vəkil" qoyur — girişi nəzarət altına alır.

**Virtual Proxy** — obyekti lazım olanadək yaratmır. İlk istifadə anında yaradılır.

---

## Problem

```java
Report report = new HeavyReport(); // ← obyekt dərhal yaradılır
// amma bəlkə heç istifadə edilməyəcək!
```

`HeavyReport` constructor-da 3 saniyəlik ağır hesablama aparır. Əgər `generate()` heç çağırılmayacaqsa bu vaxt boşa gedir.

---

## Struktur

```
Report (interface)
    ▲
    ├── HeavyReport         ← Əsl implementasiya (ağır)
    └── LazyReportProxy     ← Virtual Proxy (lazy)
```

---

## Həll

### Sadə versiya — Single-thread

```java
public class LazyReportProxy implements Report {

    private Report report;

    @Override
    public String generate() {
        if (report == null) report = new HeavyReport();
        return report.generate();
    }
}
```

Single-threaded mühitdə düzgün işləyir. Amma multi-threaded mühitdə problem yaranır — iki thread eyni anda `null` görüb iki dəfə `HeavyReport` yarada bilər.

---

### Düzgün versiya — Double-Checked Locking

```java
public class LazyReportProxy implements Report {

    private volatile Report report; // volatile — thread visibility üçün

    @Override
    public String generate() {
        if (report == null) {               // 1-ci yoxlama — lock olmadan
            synchronized (this) {
                if (report == null) {       // 2-ci yoxlama — lock ilə
                    report = new HeavyReport();
                }
            }
        }
        return report.generate();
    }
}
```

**Niyə `volatile`?** CPU cache problemi — `volatile` olmadan Thread B, Thread A-nın yaratdığı obyekti görməyə bilər. `volatile` bütün thread-lərin RAM-dan oxumasını təmin edir.

**Niyə iki yoxlama?** Birinci yoxlama hər sorğuda bahalı `synchronized` blokuna girməmək üçündür. İkinci yoxlama eyni anda iki thread girsə yalnız birinin yaratmasını təmin edir.

---

## İstifadəsi

```java
// Əsl obyekt — dərhal yaradılır (3 saniyə)
Report report1 = new HeavyReport();
report1.generate();

// Proxy — dərhal yaradılmır
Report report2 = new LazyReportProxy();
report2.generate(); // ← yalnız burada HeavyReport yaradılır (3 saniyə)
report2.generate(); // ← artıq cache-dədir, dərhal qaytarır
```

**Çıxış:**
```
HeavyReport yaradılır... (3 saniyəlik hesablama)
Hesabat: 1.250.000 sətir data

HeavyReport yaradılır... (3 saniyəlik hesablama)  ← proxy ilk çağırışda
Hesabat: 1.250.000 sətir data
```

---

## Spring-də Virtual Proxy

Spring `@Lazy` annotation-u eyni prinsipi tətbiq edir:

```java
@Service
@Lazy // ← Bean ilk istifadə anında yaradılır
public class HeavyReportService {
    // ...
}
```