# Proxy Pattern (Caching) — Cache Mexanizmi

## Pattern haqqında

**Caching Proxy** təkrarlanan sorğuların nəticəsini saxlayır. Eyni sorğu gələndə əsl obyektə getmək əvəzinə cache-dən qaytarır.

**Real dünya analoqu:** Brauzer şəkilləri cache-ləyir. Eyni sayta ikinci dəfə girəndə şəkillər serverdən yox, lokal diskdən yüklənir.

---

## Struktur

```
WeatherService (interface)
    ▲
    ├── RealWeatherService     ← Əsl implementasiya (API sorğusu)
    └── CachingWeatherProxy    ← Caching Proxy
            │ istifadə edir
            └── CacheData      ← Cache entry (dəyər + vaxt məlumatı)
```

---

## CacheData

Cache entry-ni saxlayan köməkçi class. Yaradılma vaxtı, bitmə vaxtı və dəyəri saxlayır.

```java
public class CacheData {

    private final String value;
    private final LocalDateTime expiredAt;

    public CacheData(String value, LocalDateTime expiredAt) {
        this.value = value;
        this.expiredAt = expiredAt;
    }

    public boolean isExpired() {
        return expiredAt.isBefore(LocalDateTime.now());
    }

    public String getValue() {
        return value;
    }

}

```

---

## CachingWeatherProxy

```java
public class CachingWeatherProxy implements WeatherService {

    private final long ttlInSeconds;
    private final WeatherService weatherService;
    private final Map<String, CacheData> data;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public CachingWeatherProxy(WeatherService weatherService, long ttlInSeconds) {
        this.weatherService = weatherService;
        this.ttlInSeconds = ttlInSeconds;
        this.data = new HashMap<>();
    }

    @Override
    public String getWeather(String city) {
        CacheData cacheData = data.get(city);

        if (cacheData != null && !cacheData.isExpired()) {
            System.out.printf("%s — cache-dən qaytarıldı%n", city);
            return cacheData.getValue();
        }

        String result = weatherService.getWeather(city);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusSeconds(ttlInSeconds);

        data.put(city, new CacheData(result, expiredAt));
        System.out.printf("%s — yaradıldı: %s, bitmə: %s%n", city, now.format(FMT), expiredAt.format(FMT));

        return result;
    }

}
```

---

## İstifadəsi

```java
WeatherService real    = new RealWeatherService();
WeatherService caching = new CachingWeatherProxy(real, 600); // 10 dəqiqə TTL

caching.getWeather("Bakı"); // API sorğusu + cache yazıldı
caching.getWeather("Bakı"); // cache-dən qaytarıldı
caching.getWeather("Bakı"); // cache-dən qaytarıldı
// 10 dəqiqə sonra...
caching.getWeather("Bakı"); // API sorğusu + cache yeniləndi
```

**Çıxış:**
```
API sorğusu: Bakı
Bakı: cache yaradıldı 14:35:10, bitmə vaxtı 14:45:10
Bakı — cache-dən qaytarıldı
Bakı — cache-dən qaytarıldı
API sorğusu: Bakı
Bakı: cache yaradıldı 14:45:11, bitmə vaxtı 14:55:11
```

## Spring-də Caching Proxy

Spring `@Cacheable` annotation-u eyni prinsipi tətbiq edir:

```java
@Service
public class WeatherService {

    @Cacheable(value = "weather", key = "#city") // ← Caching Proxy
    public String getWeather(String city) {
        // yalnız ilk dəfə çağırılır, sonra cache-dən qaytarılır
        return callExternalApi(city);
    }
}
```
TTL konfiqurasiyası `application.properties`-dən idarə edilir — kod dəyişmir.