# Bridge Pattern — Bank Köçürmə Sistemi

## Pattern haqqında

**Bridge** structural design pattern-lərindən biridir. Abstraksiyanı implementasiyadan ayırır - ikisi müstəqil inkişaf edə bilir.

**Real dünya analoqu:** Daxili köçürmə var, xarici köçürmə var - bunlar abstraksiyadır. ABAZ, SWIFT, IPS — bunlar implementasiyadır. Yeni kanal əlavə olunanda köçürmə tipləri dəyişmir, yeni köçürmə tipi əlavə olunanda kanallar dəyişmir.

---

## Problem - Bridge olmadan

```
InternalAbazTransfer
InternalSwiftTransfer
InternalIpsTransfer
ExternalAbazTransfer
ExternalSwiftTransfer
ExternalIpsTransfer
InternationalAbazTransfer
InternationalSwiftTransfer
InternationalIpsTransfer
```

3 köçürmə tipi × 3 kanal = **9 class**. Yeni kanal əlavə olunsa → 3 class daha. İdarəolunmazdır.

---

## Həll - İki müstəqil iyerarxiya

```
Abstraksiya (Köçürmə tipi)     İmplementasiya (Kanal)
──────────────────────────     ──────────────────────
BankTransfer                   TransferChannel
    ├── InternalTransfer            ├── ABAZChannel
    ├── ExternalTransfer            ├── SWIFTChannel
    └── InternationalTransfer       └── IPSChannel
```

---

## TransferChannel (interface)

```java
public interface TransferChannel {
    String getName();
    void process(String from, String to, double amount);
    boolean isAvailable();
}
```

---

## Kanal implementasiyaları

```java
public class ABAZChannel implements TransferChannel {

    @Override
    public String getName() { return "ABAZ şəbəkəsi"; }

    @Override
    public void process(String from, String to, double amount) {
        System.out.printf("[ABAZ] %s → %s | %.2f AZN | Komissiya: %.2f AZN%n", from, to, amount, amount * 0.001);
    }

    @Override
    public boolean isAvailable() { return true; }
}

public class SWIFTChannel implements TransferChannel {

    @Override
    public String getName() { return "SWIFT şəbəkəsi"; }

    @Override
    public void process(String from, String to, double amount) {
        System.out.printf("[SWIFT] %s → %s | %.2f USD | Komissiya: 5.00 USD%n", from, to, amount);
    }

    @Override
    public boolean isAvailable() {
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.of(8, 0)) && now.isBefore(LocalTime.of(22, 0));
    }
}

public class IPSChannel implements TransferChannel {

    @Override
    public String getName() { return "IPS şəbəkəsi"; }

    @Override
    public void process(String from, String to, double amount) {
        System.out.printf("[IPS] %s → %s | %.2f AZN | IPS köçürmə | Komissiya: 0.00%n", from, to, amount);
    }

    @Override
    public boolean isAvailable() { return true; }
}
```

---

## BankTransfer — Abstraksiya

`isAvailable()` yoxlaması mərkəzləşdirilib — hər alt class-da təkrarlamaq lazım deyil.

```java
public abstract class BankTransfer {

    protected final TransferChannel transferChannel;

    protected BankTransfer(TransferChannel transferChannel) {
        this.transferChannel = transferChannel;
    }

    abstract String getName();

    void execute(String from, String to, double amount) {
        if (!transferChannel.isAvailable()) {
            throw new IllegalStateException("%s hal-hazırda əlçatmazdır".formatted(transferChannel.getName()));
        }
        transferChannel.process(from, to, amount);
        printSummary(from, to, amount);
    }

    void printSummary(String from, String to, double amount) {
        System.out.printf("📋 %s | %s → %s | %.2f AZN | %s%n", getName(), from, to, amount, transferChannel.getName());
    }
}
```

---

## Köçürmə tipləri

```java
public class InternalTransfer extends BankTransfer {

    public InternalTransfer(TransferChannel transferChannel) {
        super(transferChannel);
    }

    @Override
    String getName() { return "Daxili köçürmə"; }

    @Override
    void execute(String from, String to, double amount) { super.execute(from, to, amount); }
}

public class ExternalTransfer extends BankTransfer {

    private static final int MAX_LIMIT = 20000;

    public ExternalTransfer(TransferChannel transferChannel) {
        super(transferChannel);
    }

    @Override
    String getName() {
        return "Xarici köçürmə";
    }

    @Override
    void execute(String from, String to, double amount) {
        if (amount >= MAX_LIMIT) {
            throw new IllegalArgumentException("%s limiti aşıldı. Max: %s AZN, cari: %.2f AZN"
                    .formatted(getName(), MAX_LIMIT, amount));
        }
        super.execute(from, to, amount);
    }

}

public class InternationalTransfer extends BankTransfer {

    private final static int MAX_LIMIT = 50000;

    public InternationalTransfer(TransferChannel transferChannel) {
        super(transferChannel);
    }

    @Override
    String getName() {
        return "Beynəlxalq köçürmə";
    }

    @Override
    void execute(String from, String to, double amount) {
        if (amount >= MAX_LIMIT) {
            throw new IllegalArgumentException("%s limiti aşıldı. Max: %s AZN, cari: %.2f AZN"
                    .formatted(getName(), MAX_LIMIT, amount));
        }
        super.execute(from, to, amount);
    }

}

```

---

## MainService

```java
public class MainService {

    public static void main(String[] args) {

        TransferChannel abaz  = new ABAZChannel();
        TransferChannel swift = new SWIFTChannel();
        TransferChannel ips   = new IPSChannel();

        System.out.println("═══ Daxili köçürmələr ═══");
        BankTransfer internal = new InternalTransfer(abaz);
        internal.execute("AZ94...1234", "AZ94...5678", 500);
        internal.execute("AZ94...1234", "AZ94...5678", 15000);

        System.out.println("\n═══ Xarici köçürmələr ═══");
        BankTransfer external = new ExternalTransfer(ips);
        external.execute("AZ94...1234", "AZ94...9999", 5000);

        try {
            external.execute("AZ94...1234", "AZ94...9999", 20000);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }

        System.out.println("\n═══ Beynəlxalq köçürmələr ═══");
        BankTransfer international = new InternationalTransfer(swift);

        try {
            international.execute("AZ94...1234", "DE89...5678", 10000);
        } catch (IllegalStateException e) {
            System.out.println("❌ " + e.getMessage());
        }

        try {
            international.execute("AZ94...1234", "DE89...5678", 50000);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }

        System.out.println("\n═══ Bridge — eyni tip, fərqli kanal ═══");
        new InternalTransfer(abaz).execute("AZ94...1234", "AZ94...5678", 200);
        new InternalTransfer(ips).execute("AZ94...1234", "AZ94...5678", 200);
    }
}
```

## Bridge-in əsas ideyası

```java
// Eyni köçürmə tipi — fərqli kanal
new InternalTransfer(abaz).execute(...);  // ABAZ ilə
new InternalTransfer(ips).execute(...);   // IPS ilə — class dəyişmir

// Eyni kanal — fərqli köçürmə tipi
new InternalTransfer(swift).execute(...);
new ExternalTransfer(swift).execute(...); // kanal dəyişmir
```

Yeni kanal əlavə olunanda (`SEPAChannel`) — köçürmə tipləri dəyişmir.
Yeni köçürmə tipi əlavə olunanda (`BulkTransfer`) — kanallar dəyişmir.