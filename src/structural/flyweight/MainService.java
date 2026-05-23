package structural.flyweight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainService {

    public static void main(String[] args) {
        List<Notification> notifications = new ArrayList<>();

        for (int i = 0; i < 1_000; i++) {
            notifications.add(new Notification("TRANSFER", "500 AZN köçürüldü", "ali@example.com", LocalDateTime.now()));
            notifications.add(new Notification("PAYMENT", "120 AZN ödənildi", "vali@example.com", LocalDateTime.now()));
            notifications.add(new Notification("SECURITY", "Yeni cihazdan giriş", "pirvali@example.com", LocalDateTime.now()));
            notifications.add(new Notification("CAMPAIGN", "50% endirim!", "rafiq@example.com", LocalDateTime.now()));
        }

        System.out.println("Cache ölçüsü: " + NotificationTypeFactory.getCacheSize()); // → 4

        notifications.forEach(Notification::display);
    }

}