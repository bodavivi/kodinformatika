package hu.ki.kodinformatika.repository;

import hu.ki.kodinformatika.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

  Optional<Url> findBySourceUrl(String sourceUrl);

  Optional<Url> findByShortUrl(String shortUrl);

}
