package com.wissen.urlShortner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wissen.urlShortner.service.UrlShortnerConfigurationService;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
@SpringBootApplication
public class UrlShortnerConfigServerApplication  {

	@Autowired
	UrlShortnerConfigurationService configService;
	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerConfigServerApplication.class, args);
	}

	/*
	public void run(String... args) throws Exception {
		configService.generateApplicationConfiguration();	
	}*/

}
