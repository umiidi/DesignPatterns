package structural.flyweight;

import java.util.HashMap;
import java.util.Map;

public class NotificationTypeFactory {

    private static final Map<String, NotificationType> cache = new HashMap<>();

    public static NotificationType getType(String code) {
        return cache.computeIfAbsent(code, k -> switch (k) {
            case "TRANSFER" -> new NotificationType(k, "Köçürmə",        "↔️",  "#1A73E8");
            case "PAYMENT"  -> new NotificationType(k, "Ödəniş",         "💳",  "#34A853");
            case "SECURITY" -> new NotificationType(k, "Təhlükəsizlik",  "🔐",  "#EA4335");
            case "CAMPAIGN" -> new NotificationType(k, "Kampaniya",      "🎯",  "#FBBC04");
            default -> throw new IllegalArgumentException("Naməlum tip: " + k);
        });
    }

    public static int getCacheSize() {
        return cache.size();
    }

}