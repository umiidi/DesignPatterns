package structural.facade;

public class MainService {

    public static void main(String[] args) {
        BankOnboardingFacade onboardingFacade = new BankOnboardingFacade(
                new KycService(),
                new AccountService(),
                new CardService(),
                new WelcomeNotificationService()
        );

        var onboardingResult1 = onboardingFacade.onboard("Ali Həsənov", "1234567", "ali@example.com");
        System.out.println(onboardingResult1.describe());

        onboardingFacade.onboard("Ali Həsənov", "12345678", "ali@example.com"); // kyc exception
    }

}
