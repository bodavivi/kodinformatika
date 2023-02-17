package hu.ki.kodinformatika.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlShortenerService {
  public static String generateShortUrl() {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder urlBuilder = new StringBuilder();
    int length = random.nextInt(8 - 3) + 3;
    for (int i = 0; i < length; i++) {
      int codeLength = random.nextInt(chars.length());
      urlBuilder.append(chars.charAt(codeLength));
    }
    return urlBuilder.toString();
  }
}
