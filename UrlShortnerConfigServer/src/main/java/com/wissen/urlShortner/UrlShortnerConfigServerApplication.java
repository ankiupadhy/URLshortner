package com.wissen.urlShortner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wissen.urlShortner.configurationService.UrlShortnerConfigurationService;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
@SpringBootApplication
public class UrlShortnerConfigServerApplication implements CommandLineRunner {

	@Autowired
	UrlShortnerConfigurationService configService;
	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerConfigServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		configService.generateApplicationConfiguration("D:/workspace/UrlShortnerConfigServer/bin/main/url-shortner.properties");	
	}

}
