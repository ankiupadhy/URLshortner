package com.wissen.urlShortner.model;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class PropertyDto {
	String key;
	String value;
	public PropertyDto() {
		super();
	}
	public PropertyDto(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "PropertyDto [key=" + key + ", value=" + value + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyDto other = (PropertyDto) obj;
		return Objects.equals(key, other.key) && Objects.equals(value, other.value);
	}
}
