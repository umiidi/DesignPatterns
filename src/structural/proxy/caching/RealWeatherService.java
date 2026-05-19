package structural.proxy.caching;

public class RealWeatherService implements WeatherService {

    @Override
    public String getWeather(String city) {
        System.out.println("API sorğusu: " + city);
        // Xarici API simulyasiyası
        return city + ": 28°C, Günəşli";
    }

}
