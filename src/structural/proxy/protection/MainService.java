package structural.proxy.protection;

import static structural.proxy.protection.Role.ADMIN;
import static structural.proxy.protection.Role.USER;

public class MainService {

    public static void main(String[] args) {
        FileManager realFileManager = new RealFileManager();

        FileManager securityFileManager1 = new SecureFileManagerProxy(ADMIN, realFileManager);
        securityFileManager1.write("fileName", "content");

        FileManager securityFileManager2 = new SecureFileManagerProxy(USER, realFileManager);
        securityFileManager2.write("fileName", "content"); // throw SecurityException
    }

}
