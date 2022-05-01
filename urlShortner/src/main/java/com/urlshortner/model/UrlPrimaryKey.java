package com.urlshortner.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.urlshortner.model.UserForUrlShortner;
@Embeddable
public class UrlPrimaryKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7350965951552394778L;

	String Id;
	
	@JoinColumn(name = "UserForUrlShortner.userId")
	String userId;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UrlPrimaryKey [Id=" + Id + ", userId=" + userId + "]";
	}

	public UrlPrimaryKey(String id, String userId) {
		super();
		Id = id;
		this.userId = userId;
	}

	public UrlPrimaryKey() {
		super();
	}
	
	
}
