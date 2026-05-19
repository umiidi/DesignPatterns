package structural.proxy.caching;

import java.time.LocalDateTime;

public class CacheData {

    private final String value;
    private final LocalDateTime expiredAt;

    public CacheData(String value, LocalDateTime expiredAt) {
        this.value = value;
        this.expiredAt = expiredAt;
    }

    public boolean isExpired() {
        return expiredAt.isBefore(LocalDateTime.now());
    }

    public String getValue() {
        return value;
    }

}
