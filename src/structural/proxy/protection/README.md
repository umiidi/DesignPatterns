# Proxy Pattern (Protection) — Giriş Nəzarəti

## Pattern haqqında

**Protection Proxy** istifadəçinin icazəsinə görə əsl obyektə girişi nəzarət altına alır. Əsl obyekt yalnız icazəsi olan istifadəçilərə əlçatan olur.

**Real dünya analoqu:** Ofis binasına girişdə təhlükəsizlik var. Adi işçi yalnız öz mərtəbəsinə keçə bilir, direktor bütün mərtəbələrə.

---

## Struktur

```
FileManager (interface)
    ▲
    ├── RealFileManager          ← Əsl implementasiya
    └── SecureFileManagerProxy   ← Protection Proxy
```

---

## Həll

```java
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
```

---

## İstifadəsi

```java
FileManager realFileManager = new RealFileManager();

FileManager securityFileManager1 = new SecureFileManagerProxy(ADMIN, realFileManager);
securityFileManager1.write("fileName", "content");  // ✅

FileManager securityFileManager2 = new SecureFileManagerProxy(USER, realFileManager);
securityFileManager2.write("fileName", "content");  // ❌ SecurityException
```

---

## Spring-də Protection Proxy

Spring Security eyni prinsipi annotation ilə tətbiq edir:

```java
@Service
public class FileService {

    @PreAuthorize("hasRole('ADMIN')") // ← Protection Proxy
    public void write(String fileName, String content) {
        // ...
    }

    public String read(String fileName) {
        return "content"; // hamıya açıqdır
    }
}
```
Spring araya AOP Proxy qoyur — metod çağırılmadan əvvəl rolu yoxlayır, icazə yoxdursa `AccessDeniedException` atır.