package structural.facade;

public class AccountService {

    public String createAccount(String fullName, String type) {
        String accountNumber = "AZ" + (int)(Math.random() * 100000000);
        System.out.println("Hesab yaradıldı: " + accountNumber);
        return accountNumber;
    }

}
