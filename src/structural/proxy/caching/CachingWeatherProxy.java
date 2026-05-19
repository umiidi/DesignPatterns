package structural.proxy.caching;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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
