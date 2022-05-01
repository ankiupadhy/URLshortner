package com.urlshortner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.urlshortner.model.Url;
import com.urlshortner.model.UrlPrimaryKey;

@Repository
public interface UrlRepository extends CrudRepository<Url, UrlPrimaryKey> {

	Url findByoriginalUrl(String originalUrl);
	Url findByshortUrl(String shortUrl);
	List<Url> findAll();
	Optional<Url> findByUrlPrimaryKey(UrlPrimaryKey urlPrimaryKey);
}
