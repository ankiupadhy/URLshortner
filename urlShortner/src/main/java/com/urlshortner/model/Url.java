package com.urlshortner.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Url implements Serializable {


	private static final long serialVersionUID = 3920482674676613873L;

	@EmbeddedId
	UrlPrimaryKey urlPrimaryKey;
	
	@Column
	String originalUrl;

	@Column
	String shortUrl;

	public UrlPrimaryKey getUrlPrimaryKey() {
		return urlPrimaryKey;
	}

	public void setUrlPrimaryKey(UrlPrimaryKey urlPrimaryKey) {
		this.urlPrimaryKey = urlPrimaryKey;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(originalUrl, shortUrl, urlPrimaryKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Url other = (Url) obj;
		return Objects.equals(originalUrl, other.originalUrl) && Objects.equals(shortUrl, other.shortUrl)
				&& Objects.equals(urlPrimaryKey, other.urlPrimaryKey);
	}

	public Url(UrlPrimaryKey urlPrimaryKey, String originalUrl, String shortUrl) {
		super();
		this.urlPrimaryKey = urlPrimaryKey;
		this.originalUrl = originalUrl;
		this.shortUrl = shortUrl;
	}

	public Url() {
		super();
	}
	
	
}
