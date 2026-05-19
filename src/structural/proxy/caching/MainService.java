package structural.proxy.caching;

public class MainService {

    public static void main(String[] args) throws InterruptedException {
        WeatherService realWeatherService = new RealWeatherService();
        WeatherService cachingWeatherService = new CachingWeatherProxy(realWeatherService, 600);

        cachingWeatherService.getWeather("Bakı"); // API sorğusu + cache yazıldı
        cachingWeatherService.getWeather("Bakı"); // cache-dən qaytarıldı
        cachingWeatherService.getWeather("Bakı"); // cache-dən qaytarıldı
        // 10 dəqiqə sonra...
        cachingWeatherService.getWeather("Bakı"); // API sorğusu + cache yeniləndi
    }

}
