package com.wissen.urlShortner.controller;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.urlShortner.model.PropertyDto;
import com.wissen.urlShortner.service.UrlShortnerConfigurationService;

@RestController
@RequestMapping("/api/config/v1")
public class UrlShortnerConfigurationController {

	@Autowired
	UrlShortnerConfigurationService configService;
	
	@PostMapping("/properties")
	public String generateApplicationConfig() {
		return configService.generateApplicationConfiguration();
	}
	
	@GetMapping("/properties")
	public List<PropertyDto> getActiveInstanceConfig(@RequestParam String applicationName,@RequestParam String instanceName) throws IOException, InterruptedException, KeeperException{
		return configService.getActiveInstanceConfiguartion(applicationName, instanceName);
	}
	
	/*@PutMapping(value="/properties",params = {"applicationName", "instanceName"})
	public String updateInstanceSpecificConfig(@RequestParam String applicationName,@RequestParam String instanceName, @RequestBody List<PropertyDto> properties) throws IOException, InterruptedException, KeeperException{
		return configService.updateApplicationConfiguration(applicationName, instanceName,properties);
	}
	
	@PutMapping(value="/properties",params = {"applicationName"})
	public String updateInstanceSpecificConfig(@RequestParam String applicationName, @RequestBody List<PropertyDto> properties) throws IOException, InterruptedException, KeeperException{
		return configService.updateApplicationConfiguration(applicationName,properties);
	}*/
	
	
	
}
