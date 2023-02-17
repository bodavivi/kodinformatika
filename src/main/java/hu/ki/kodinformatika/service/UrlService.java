package hu.ki.kodinformatika.service;

import hu.ki.kodinformatika.cache.UrlCache;
import hu.ki.kodinformatika.model.Url;
import hu.ki.kodinformatika.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
public class UrlService {

  @Autowired
  private UrlRepository urlRepository;

  @Autowired
  private UrlCache urlCache;

  @Value("${shortened-url.base-path}")
  private String basePath;

  public Url shortenUrl(String sourceUrl) {
    log.info("Shorten '" + sourceUrl + "' url has been started.");
    Optional<Url> savedUrl = urlRepository.findBySourceUrl(sourceUrl);
    if (savedUrl.isPresent()) {
      return savedUrl.get();
    } else {
      validateUrl(sourceUrl);
      String shortUrl = basePath + "/" + UrlShortenerService.generateShortUrl();
      Url url = Url.builder()
          .sourceUrl(sourceUrl)
          .shortUrl(shortUrl)
          .build();
      return urlRepository.save(url);
    }
  }

  private void validateUrl(String url) {
    UrlValidator validator = new UrlValidator();
    if (!validator.isValid(url)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "The given url is not valid!");
    }
  }

  public String getSourceUrlByBasePathAndShortUrl(String shortUrl) {
    String sourceUrl = urlCache.get(shortUrl);
    if (sourceUrl == null) {
      sourceUrl = urlRepository.findByShortUrl(shortUrl).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given short url is not exists")).getSourceUrl();
      urlCache.add(shortUrl, sourceUrl);
    }
    return sourceUrl;
  }
}
