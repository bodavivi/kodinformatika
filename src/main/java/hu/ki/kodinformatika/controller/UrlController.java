package hu.ki.kodinformatika.controller;

import hu.ki.kodinformatika.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/url")
public class UrlController {

  @Autowired
  private UrlService urlService;

  @PostMapping("/shorten/**")
  public String shortenUrl(HttpServletRequest request) {
    String sourceUrl = request.getRequestURL().toString().split("/shorten/")[1];
    return urlService.shortenUrl(sourceUrl).getShortUrl();
  }

  @GetMapping("/{basePath}/{shortUrl}")
  public ModelAndView redirectToSourceUrl(@PathVariable String basePath, @PathVariable String shortUrl) {
    String sourceUrl = urlService.getSourceUrlByBasePathAndShortUrl(basePath + "/" + shortUrl);
    return new ModelAndView("redirect:" + sourceUrl);
  }
}
