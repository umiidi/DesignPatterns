# Facade Pattern — Bank Onboarding Sistemi

## Pattern haqqında

**Facade** structural design pattern-lərindən biridir. Mürəkkəb alt sistemləri arxada gizlədib client-ə sadə bir interfeys təqdim edir.

**Real dünya analoqu:** Restoranda sifariş verirsən — "bir pizza" deyirsən. Arxada xəmir hazırlanır, soba qızır, çatdırılma təşkil edilir. Sən bunların heç birini bilmirsən — yalnız ofisiantla danışırsan.

---

## Əvvəlki pattern-lərlə fərqi

| | Adapter | Proxy | Facade |
|---|---|---|---|
| **Məqsəd** | İnterfeys çevir | Girişi nəzarət et | Sadələşdir |
| **Alt sistem** | Bir obyekt | Bir obyekt | Çox obyekt |
| **Client** | Fərqli interfeys | Eyni interfeys | Sadə interfeys |

---

## Struktur

```
BankOnboardingFacade
    │ istifadə edir
    ├── KycService
    ├── AccountService
    ├── CardService
    └── WelcomeNotificationService

MainService → yalnız BankOnboardingFacade tanıyır
```

---

## OnboardingResult

```java
public record OnboardingResult(String fullName, String accountNumber, String cardNumber) {

    public String describe() {
        return """
                %s üçün hesab açıldı.
                Hesab nömrəsi : %s
                Kart nömrəsi  : %s
                """.formatted(fullName, accountNumber, cardNumber);
    }
}
```

## BankOnboardingFacade

```java
public class BankOnboardingFacade {

    private final KycService kycService;
    private final AccountService accountService;
    private final CardService cardService;
    private final WelcomeNotificationService welcomeNotificationService;

    public BankOnboardingFacade(KycService kycService,
                                AccountService accountService,
                                CardService cardService,
                                WelcomeNotificationService welcomeNotificationService) {
        this.kycService = kycService;
        this.accountService = accountService;
        this.cardService = cardService;
        this.welcomeNotificationService = welcomeNotificationService;
    }

    public OnboardingResult onboard(String fullName, String pinCode, String email) {
        if (!kycService.verify(fullName, pinCode)) {
            throw new IllegalStateException("KYC yoxlaması uğursuz oldu: " + fullName);
        }

        String accountNumber = accountService.createAccount(fullName, "CURRENT");
        String cardNumber = cardService.issueCard(accountNumber, "DEBIT");
        welcomeNotificationService.send(email, accountNumber, cardNumber);

        return new OnboardingResult(fullName, accountNumber, cardNumber);
    }
}
```

**Facade-in etdiyi işlər — sıra ilə:**

1. KYC yoxla — uğursuz olarsa `IllegalStateException` at
2. `"CURRENT"` tipli hesab yarat
3. `"DEBIT"` tipli kart ver
4. Xoş gəldin emaili göndər
5. `OnboardingResult` qaytar

---

## İstifadəsi

```java
BankOnboardingFacade onboardingFacade = new BankOnboardingFacade(
        new KycService(),
        new AccountService(),
        new CardService(),
        new WelcomeNotificationService()
);

// case 1
OnboardingResult result = onboardingFacade.onboard("Ali Həsənov", "1234567", "ali@example.com");
System.out.println(result.describe());

// case 2
onboardingFacade.onboard("Ali Həsənov", "12345678", "ali@example.com"); // kyc exception

```

**Çıxış:**
```
KYC yoxlanıldı: Ali Həsənov
Hesab yaradıldı: AZ48291733
Kart verildi: 41691827364
Xoş gəldin emaili göndərildi → ali@example.com

Ali Həsənov üçün hesab açıldı.
Hesab nömrəsi : AZ48291733
Kart nömrəsi  : 41691827364
```

---

## Spring-də Facade

Spring-də `@Service` class-ları çox vaxt Facade rolunu oynayır:

```java
@Service
public class BankOnboardingService {  // ← Facade

    private final KycService kycService;
    private final AccountService accountService;
    private final CardService cardService;
    private final WelcomeNotificationService notificationService;

    public OnboardingResult onboard(OnboardingRequest request) {
        // alt sistemləri orkestrasiya edir
    }
}

// Controller yalnız Facade-i tanıyır
@RestController
public class OnboardingController {

    private final BankOnboardingService onboardingService;

    @PostMapping("/onboard")
    public OnboardingResult onboard(@RequestBody OnboardingRequest request) {
        return onboardingService.onboard(request);
    }
}
```