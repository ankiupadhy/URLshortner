package com.urlshortner.service;

import java.util.List;
import java.util.Optional;

import com.urlshortner.model.Url;
import com.urlshortner.model.UserForUrlShortner;

public interface UrlShortnerService {

	UserForUrlShortner createUser(String username,String email);
	Url generateShortUrl(String originalUrl, String userId);
	Optional<Url> getOriginalUrl(String id, String userId);
	List<Url> listAllUrls();
	List<UserForUrlShortner> listAllUsers();
}
