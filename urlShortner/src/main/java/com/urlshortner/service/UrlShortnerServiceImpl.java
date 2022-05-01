package com.urlshortner.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.apache.commons.validator.routines.UrlValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.urlshortner.model.Url;
import com.urlshortner.model.UrlPrimaryKey;
import com.urlshortner.model.UserForUrlShortner;
import com.urlshortner.repository.UrlRepository;
import com.urlshortner.repository.UserRepository;
import com.urlshortner.validator.EmailValidator;
import com.urlshortner.validator.URLValidator;
import com.google.common.hash.Hashing;
import com.urlshortner.Exception.InvalidEmailException;
import com.urlshortner.Exception.InvalidUrlException;
import com.urlshortner.Exception.InvalidUsernameException;
import com.urlshortner.Exception.NoSuchElementException;
import com.urlshortner.Exception.NullArgumentsException;
import com.urlshortner.constants.UrlShortnerConstants;

@Service
public class UrlShortnerServiceImpl implements UrlShortnerService {

	@Autowired
	UrlRepository urlRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	URLValidator urlValidator;

	@Autowired
	EmailValidator emailValidator;

	Logger LOGGER = LoggerFactory.getLogger(UrlShortnerServiceImpl.class);

	@Override
	public Url generateShortUrl(String originalUrl, String userId) {

		validateInputForGeneratingShortUrl(userId, originalUrl);
		LOGGER.info("Generating unique Id for the given URL: {}");
		String generatedId = Hashing.murmur3_32_fixed().hashString(originalUrl + userId, StandardCharsets.UTF_8)
				.toString();
		UrlPrimaryKey urlPrimaryKey = new UrlPrimaryKey(generatedId, userId);
		LOGGER.info("Generated unique Id for the given URL: {}");
		if (!urlRepo.findByUrlPrimaryKey(urlPrimaryKey).isPresent()) {
			urlRepo.save(new Url(urlPrimaryKey, originalUrl, UrlShortnerConstants.URL_PREFIX + generatedId));
			LOGGER.info("Saving the short URL into the database if does not exist: {}");
		}
		return (new Url(urlPrimaryKey, originalUrl, UrlShortnerConstants.URL_PREFIX + generatedId));
	}

	@Override
	@Cacheable(cacheNames = "url", key = "#id+'_'+#userId", sync = true)
	public Optional<Url> getOriginalUrl(String id, String userId) {
		if (id.isEmpty() || userId.isEmpty())
			throw new NullArgumentsException("Url ID and User ID should not be null");
		UrlPrimaryKey urlPrimaryKey = new UrlPrimaryKey(id, userId);
		if (urlRepo.findByUrlPrimaryKey(urlPrimaryKey).isPresent()) {
			return urlRepo.findByUrlPrimaryKey(urlPrimaryKey);
		} else {
			LOGGER.info("No entry present for given Url ID and User ID");
			throw new NoSuchElementException("No such entry present");
		}
	}

	@Override
	public UserForUrlShortner createUser(String username, String email) {
		validateInputForCreatingUser(username,email);
		UserForUrlShortner user;
		if (!userRepo.findByUsername(username).isPresent()) {
			user=userRepo.save(new UserForUrlShortner(username, email));
			LOGGER.info("Saving the user into the database if username does not exist: {}");
		} else
			throw (new InvalidUsernameException("Username already exists. Try with a differnt username"));
		return user;
	}

	@Override
	public List<Url> listAllUrls() {
		return urlRepo.findAll();
	}

	@Override
	public List<UserForUrlShortner> listAllUsers() {
		return userRepo.findAll();
	}

	public void validateInputForGeneratingShortUrl(String userId, String url) {

		if (url.isEmpty() || userId.isEmpty()) {
			LOGGER.info("User ID and Original Url should not be null");
			throw new NullArgumentsException("User ID and Original Url should not be null");
		}
		if (userRepo.findByUserId(userId).isPresent() == false) {
			LOGGER.info("User ID not valid");
			throw new NoSuchElementException("User ID not valid");
		}
		if (!urlValidator.validateUrl(url)) {
			LOGGER.info("Url is invalid");
			throw new InvalidUrlException("Url is invalid");
		}
	}
	
	public void validateInputForCreatingUser(String username,String email) {
		if (username.isEmpty() || email.isEmpty()) {
			LOGGER.info("Username and Email should not be null");
			throw new NullArgumentsException("Username and Email should not be null");
			}
		if (!emailValidator.validateEmail(email)) {
			LOGGER.info("Email address invalid");
			throw new InvalidEmailException("Email address invalid.");
		}
	}

}