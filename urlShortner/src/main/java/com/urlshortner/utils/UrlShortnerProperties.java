package com.urlshortner.utils;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class UrlShortnerProperties extends Properties {
	 
	 private static final long serialVersionUID = 1L;
	 
	 public UrlShortnerProperties(DataSource dataSource) {
	 super();
	 
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	 List<Map<String, Object>> configs = jdbcTemplate
	 .queryForList("select application,label,key,value,profile from properties");
	 
	 for (Map<String, Object> config : configs) {
	 setProperty((config.get("key")).toString(), (config.get("value")).toString());
	 }
	 }
	}
