package structural.facade;

public class KycService {

    public boolean verify(String fullName, String pinCode) {
        System.out.println("KYC yoxlanıldı: " + fullName);
        return pinCode.length() == 7;
    }

}
