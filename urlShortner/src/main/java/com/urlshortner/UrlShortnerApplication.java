package com.urlshortner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;
//import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/*
 * @author ankita.upadhyay@wissen.com
 */

@EnableCaching
@SpringBootApplication
@EnableDiscoveryClient
public class UrlShortnerApplication {

	static Logger LOGGER = LoggerFactory.getLogger(UrlShortnerApplication.class);
	
	public static void main(String[] args) {
		LOGGER.info("Started Spring Boot Application : {}");
	
		/*try {
			Server.createTcpServer().start();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		SpringApplication.run(UrlShortnerApplication.class, args);
	}
	
}