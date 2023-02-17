package hu.ki.kodinformatika.cache;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UrlCache {

  private static final int MAX_CACHE_SIZE = 100;

  private final Map<String, UrlData> cache = new LinkedHashMap<>() {
    protected boolean removeEldestEntry(Map.Entry<String, UrlData> eldest) {
      return size() > MAX_CACHE_SIZE;
    }
  };

  public void add(String shortUrl, String longUrl) {
    cache.put(shortUrl, new UrlData(longUrl));
  }

  public String get(String shortUrl) {
    UrlData sourceUrl = cache.get(shortUrl);
    if (sourceUrl != null) {
      sourceUrl.setLastAccess(LocalDateTime.now());
      return sourceUrl.getSourceUrl();
    }
    return null;
  }

  private static class UrlData {
    private final String sourceUrl;
    private LocalDateTime lastAccess;

    public UrlData(String longUrl) {
      this.sourceUrl = longUrl;
      this.lastAccess = LocalDateTime.now();
    }

    public String getSourceUrl() {
      return sourceUrl;
    }

    public void setLastAccess(LocalDateTime lastAccess) {
      this.lastAccess = lastAccess;
    }

    public LocalDateTime getLastAccess() {
      return lastAccess;
    }
  }
}
