package com.urlshortner.validator;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

@Component
public class URLValidator {
	static final String[] schemes = { "http", "https" };

	public boolean validateUrl(String url) {
		UrlValidator urlValidator = new UrlValidator(schemes, UrlValidator.ALLOW_LOCAL_URLS);
		if (urlValidator.isValid(url))
			return true;
		else
			return false;
	}

}
