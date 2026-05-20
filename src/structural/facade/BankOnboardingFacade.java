package structural.facade;

public class BankOnboardingFacade {

    private final KycService kycService;
    private final AccountService accountService;
    private final CardService cardService;
    private final WelcomeNotificationService welcomeNotificationService;

    public BankOnboardingFacade(KycService kycService, AccountService accountService, CardService cardService, WelcomeNotificationService welcomeNotificationService) {
        this.kycService = kycService;
        this.accountService = accountService;
        this.cardService = cardService;
        this.welcomeNotificationService = welcomeNotificationService;
    }

    public OnboardingResult onboard(String fullName, String pinCode, String email) {
        boolean kycVerified = kycService.verify(fullName, pinCode);
        if (!kycVerified) throw new IllegalStateException("KYC yoxlaması uğursuz oldu: " + fullName);

        String accountNumber = accountService.createAccount(fullName, "CURRENT");
        String cardNumber = cardService.issueCard(accountNumber, "DEBIT");
        welcomeNotificationService.send(email, accountNumber, cardNumber);

        return new OnboardingResult(fullName, accountNumber, cardNumber);
    }

}
