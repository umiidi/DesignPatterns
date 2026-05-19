package structural.proxy.protection;

import static structural.proxy.protection.Role.ADMIN;

public class SecureFileManagerProxy implements FileManager {

    private final Role userRole;
    private final FileManager fileManager;

    public SecureFileManagerProxy(Role userRole, FileManager fileManager) {
        this.userRole = userRole;
        this.fileManager = fileManager;
    }

    @Override
    public String read(String fileName) {
        return fileManager.read(fileName);
    }

    @Override
    public void write(String fileName, String content) {
        if (ADMIN.equals(userRole)) {
            fileManager.write(fileName, content);
        } else throw new SecurityException();
    }

}
