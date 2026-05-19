package structural.proxy.protection;

public class RealFileManager implements FileManager {

    @Override
    public String read(String fileName) {
        System.out.println("Oxundu: " + fileName);
        return "content of " + fileName;
    }

    @Override
    public void write(String fileName, String content) {
        System.out.println("Yazıldı: " + fileName + " → " + content);
    }

}