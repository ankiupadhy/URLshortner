package com.urlshortner.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.urlshortner.Exception.InvalidUrlException;
import com.urlshortner.Exception.NoSuchElementException;
import com.urlshortner.Exception.NullArgumentsException;
import com.urlshortner.model.Url;

@SpringBootTest
public class UrlShortnerServiceImplTest {

	@Autowired
	UrlShortnerServiceImpl urlShortnerServiceImpl;

	@Test
	public void test_generateShortUrl() {
		Url url = urlShortnerServiceImpl.generateShortUrl("http://tcs.com","1");
		Assertions.assertThat(url).isNotNull();
	}

	@Test
	public void testGenerateShortUrl_shouldThrowNoSuchElementExceptionWhenUserNotPresent() {
		Assertions.assertThatThrownBy(() -> urlShortnerServiceImpl.generateShortUrl("http://tcs.com","4"))
				.isInstanceOf(NoSuchElementException.class);
	}
	
	@Test
	public void testGenerateShortUrl_shouldThrowNullArgumentsExceptionWhenEmptyUrlIsPassed() {
		Assertions.assertThatThrownBy(() -> urlShortnerServiceImpl.generateShortUrl("","1"))
				.isInstanceOf(NullArgumentsException.class);
	}
	
	@Test
	public void testGenerateShortUrl_shouldThrowNullArgumentsExceptionWhenEmptyUserIdIsPassed() {
		Assertions.assertThatThrownBy(() -> urlShortnerServiceImpl.generateShortUrl("http://tcs.com",""))
				.isInstanceOf(NullArgumentsException.class);
	}
	
	@Test
	public void testGenerateShortUrl_shouldThrowNullArgumentsExceptionWhenEmptyUrlAndEmptyUserIdIsPassed() {
		Assertions.assertThatThrownBy(() -> urlShortnerServiceImpl.generateShortUrl("",""))
				.isInstanceOf(NullArgumentsException.class);
	}

	@Test
	public void testGenerateShortUrl_shouldThrowInvalidUrlExceptionWhenInvalidUrlIsPassed() {
		Assertions.assertThatThrownBy(() -> urlShortnerServiceImpl.generateShortUrl("dummy","1"))
				.isInstanceOf(InvalidUrlException.class);
	}

	@Test
	public void test_getOriginalUrl() {
		Optional<Url> url = urlShortnerServiceImpl.getOriginalUrl("b4059432","1");
		Assertions.assertThat(url).isNotNull();
	}

	@Test
	public void testGetOriginalUrl_shouldThrowNullArgumentsExceptionWhenEmptyUrlIdIsPassed() {

		Assertions.assertThatThrownBy(() -> urlShortnerServiceImpl.getOriginalUrl("","1"))
				.isInstanceOf(NullArgumentsException.class);
	}
	
	@Test
	public void testGetOriginalUrl_shouldThrowNullArgumentsExceptionWhenEmptyUserIdIsPassed() {

		Assertions.assertThatThrownBy(() -> urlShortnerServiceImpl.getOriginalUrl("b4059432",""))
				.isInstanceOf(NullArgumentsException.class);
	}
	
	@Test
	public void testGetOriginalUrl_shouldThrowNullArgumentsExceptionWhenEmptyUrlIdAndEmptyUserIdIsPassed() {

		Assertions.assertThatThrownBy(() -> urlShortnerServiceImpl.getOriginalUrl("",""))
				.isInstanceOf(NullArgumentsException.class);
	}

	@Test
	public void testGetOriginalUrl_shouldThrowNoSuchElementExceptionWhenInvalidUrlIdIsPassed() {

		Assertions.assertThatThrownBy(() -> urlShortnerServiceImpl.getOriginalUrl("b4059432x","1"))
				.isInstanceOf(NoSuchElementException.class);
	}
	
	@Test
	public void testGetOriginalUrl_shouldThrowNoSuchElementExceptionWhenInvalidUserIdIsPassed() {

		Assertions.assertThatThrownBy(() -> urlShortnerServiceImpl.getOriginalUrl("b4059432","3"))
				.isInstanceOf(NoSuchElementException.class);
	}

}
