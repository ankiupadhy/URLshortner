package com.urlshortner.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortner.model.Url;
import com.urlshortner.model.UserForUrlShortner;
import com.urlshortner.service.UrlShortnerServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class UrlShortnerController {
	
	@Autowired
	UrlShortnerServiceImpl urlShortnerService;
	
	@Value("${server.port}")
	 String port;
	
	@PostMapping(value="/users")
	UserForUrlShortner createUser(@RequestParam String username, @RequestParam String email) {
		return urlShortnerService.createUser(username, email);
	}

	@PostMapping( value="/user/url")
	Url generateShortUrl(@RequestParam String userId, @RequestParam String originalUrl) {
		return urlShortnerService.generateShortUrl(originalUrl,userId);
	}
	
	@GetMapping( value="/user/url")
	Optional<Url> getOriginaltUrl(@RequestParam String urlId, @RequestParam String userId) {
		return urlShortnerService.getOriginalUrl(urlId,userId);
	}
	
	@GetMapping( value="/urls")
	List<Url> listAllUrls() {
		return urlShortnerService.listAllUrls();
	}
	
	@GetMapping( value="/users")
	List<UserForUrlShortner> listAllUsers() {
		return urlShortnerService.listAllUsers();
	}
	
	@GetMapping(value="/port")
	String getPort() {
		return port;
	}
}
