package structural.proxy.protection;

public interface FileManager {
    String read(String fileName);
    void write(String fileName, String content);
}
