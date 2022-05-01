package com.urlshortner.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UrlShortnerExceptionHandler {
	
	@ExceptionHandler(NullArgumentsException.class)
	public ResponseEntity<String> handleNullArgumentsException(NullArgumentsException nullArgumentsException){
		return new ResponseEntity<String>("Arguments should not be null",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidUrlException.class)
	public ResponseEntity<String> handleInavlidUrlException(InvalidUrlException invalidUrlException){
		return new ResponseEntity<String>("Url is invalid", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException invalidUrlException){
		return new ResponseEntity<String>("No such URL present, Verify user ID and url ID", HttpStatus.NOT_FOUND);
	}
}
