package structural.flyweight;

import java.time.LocalDateTime;

public class Notification {

    private final NotificationType type;
    private final String message;
    private final String recipient;
    private final LocalDateTime time;

    public Notification(String typeCode, String message, String recipient, LocalDateTime time) {
        this.type = NotificationTypeFactory.getType(typeCode);
        this.message = message;
        this.recipient = recipient;
        this.time = time;
    }

    public void display() {
        if (type.isUrgent()) System.out.print("❗ ");
        type.render(message, recipient, time);
    }

}
