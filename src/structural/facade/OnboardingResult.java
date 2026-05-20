package structural.facade;

public record OnboardingResult(String fullName, String accountNumber, String cardNumber) {

    public String describe() {
        return """
                %s üçün hesab açıldı.
                Hesab nömrəsi : %s
                Kart nömrəsi  : %s
                """.formatted(fullName, accountNumber, cardNumber);
    }

}
