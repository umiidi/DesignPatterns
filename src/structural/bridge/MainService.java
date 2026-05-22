package structural.bridge;

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

        // limit keçən — exception
        try {
            external.execute("AZ94...1234", "AZ94...9999", 20000);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Xarici köçürmə limiti aşıldı: 20,000 AZN");
        }

        System.out.println("\n═══ Beynəlxalq köçürmələr ═══");
        BankTransfer international = new InternationalTransfer(swift);

        // SWIFT isAvailable() yoxlanılır
        try {
            international.execute("AZ94...1234", "DE89...5678", 10000);
        } catch (IllegalStateException e) {
            System.out.println("❌ " + e.getMessage());
        }

        // limit keçən — exception
        try {
            international.execute("AZ94...1234", "DE89...5678", 50000);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Beynəlxalq köçürmə limiti aşıldı: 50,000 USD");
        }

        System.out.println("\n═══ Bridge — eyni tip, fərqli kanal ═══");
        // eyni InternalTransfer — fərqli kanal
        new InternalTransfer(abaz).execute("AZ94...1234", "AZ94...5678", 200);
        new InternalTransfer(ips).execute("AZ94...1234", "AZ94...5678", 200);
    }
}
