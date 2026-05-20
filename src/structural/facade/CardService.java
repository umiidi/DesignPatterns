package structural.facade;

public class CardService {

    public String issueCard(String accountNumber, String cardType) {
        String cardNumber = "4169" + (int)(Math.random() * 1000000000);
        System.out.println("Kart verildi: " + cardNumber);
        return cardNumber;
    }

}
