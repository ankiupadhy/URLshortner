package com.wissen.urlShortnerDiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class UrlShortnerDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerDiscoveryApplication.class, args);
	}

}
