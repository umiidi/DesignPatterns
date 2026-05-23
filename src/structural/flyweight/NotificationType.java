package structural.flyweight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public void render(String message, String recipient, LocalDateTime time) {
        System.out.printf("%s [%s] %s | %s | %s%n",
                icon, label, recipient, message,
                time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
    }

    public boolean isUrgent() {
        return "SECURITY".equals(code);
    }

}
