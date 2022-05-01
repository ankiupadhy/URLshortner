package com.wissen.urlShortner.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Service;

import com.urlShortner.constants.Constants;
import com.wissen.urlShortner.model.PropertyDto;

@Service
public class UrlShortnerConfigurationService {
	@Value("${zookeeper.hostname}")
	String host;
	
	@Autowired
	ZookeeperConnection conn;
	
	public String generateApplicationConfiguration() {
		try {
			ZooKeeper zk=conn.connect(host);
			URL folder = getClass().getResource(Constants.SOURCE_CONFIGURATION_FOLDER);
			Path dirPath = Paths.get(folder.toURI());
			Stream<Path> paths = Files.list(dirPath);
			List<String> propertyFileNames=new ArrayList<>();
			List<String> propertyFilePaths =new ArrayList<>();
			paths.forEach(s->{
				propertyFileNames.add(s.getFileName().toString());
				propertyFilePaths.add(s.toString());
			});
			
			int counter=0;
			for(String propertyFileName: propertyFileNames ) {
				String znode="";
				if(zk.exists(Constants.SOURCE_ZNODE, true)==null)
					zk.create(Constants.SOURCE_ZNODE, null,ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				if(!propertyFileName.contains(",")) {
					if(zk.exists(Constants.COMMON_PROPERTY_ZNODE, true)==null)
						zk.create(Constants.COMMON_PROPERTY_ZNODE, null,ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
				else {
					znode=propertyFileName.substring(0,propertyFileName.indexOf("."));
					if(zk.exists(Constants.SOURCE_ZNODE+znode, true)==null)
						zk.create(Constants.SOURCE_ZNODE+Constants.ZNODE_PATH_SEPERATOR+znode, null,ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
				List<PropertyDto> properties = new ArrayList<>();
				List<String> propertiesFromFile = Files.readAllLines(Paths.get(propertyFilePaths.get(counter)));
				for (String property : propertiesFromFile) {
					properties.add(new PropertyDto(property.split("=")[0],property.split("=")[1]));
					if(propertyFileName.contains(",")) {
						zk.create(Constants.SOURCE_ZNODE+Constants.ZNODE_PATH_SEPERATOR+znode+Constants.ZNODE_PATH_SEPERATOR+property.split("=")[0],(property.split("=")[1]).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
					else
					{
						zk.create(Constants.COMMON_PROPERTY_ZNODE+Constants.ZNODE_PATH_SEPERATOR+property.split("=")[0],(property.split("=")[1]).getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}	
				}counter++;
			}
			zk.close();
		}
		
		 catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.CONFIGURATION_LOADED_MESSAGE;
		
	}
	
	public List<PropertyDto> getActiveInstanceConfiguartion(String applicationName,String instanceName) throws IOException, InterruptedException, KeeperException{
		List<PropertyDto> properties=new ArrayList<>();
		ZooKeeper zk=conn.connect(host);
		List<String> commonProperties=zk.getChildren(Constants.COMMON_PROPERTY_ZNODE, true);
		for(String propertyName:commonProperties) {
			properties.add(new PropertyDto(propertyName,
					new String(zk.getData(Constants.COMMON_PROPERTY_ZNODE+Constants.ZNODE_PATH_SEPERATOR+propertyName, true, zk.exists(Constants.COMMON_PROPERTY_ZNODE+Constants.ZNODE_PATH_SEPERATOR+propertyName,true)),StandardCharsets.UTF_8)
					));
		}
		List<String> instanceSpecificProperties=zk.getChildren(Constants.SOURCE_ZNODE+Constants.ZNODE_PATH_SEPERATOR+applicationName+","+instanceName, true);
		for(String propertyName:instanceSpecificProperties) {
			properties.add(new PropertyDto(propertyName,new String(
					zk.getData(Constants.SOURCE_ZNODE+Constants.ZNODE_PATH_SEPERATOR+applicationName+","+instanceName+Constants.ZNODE_PATH_SEPERATOR+propertyName, true, zk.exists(Constants.SOURCE_ZNODE+Constants.ZNODE_PATH_SEPERATOR+applicationName+","+instanceName+propertyName,true)),StandardCharsets.UTF_8)
					));
		}
		zk.close();
		return properties;
	}
/*	
	public String updateApplicationConfiguration(String applicationName,String instanceName,List<PropertyDto> properties) throws IOException, InterruptedException, KeeperException {
		ZooKeeper zk=conn.connect(host);
		for(PropertyDto property: properties)
		zk.setData(Constants.SOURCE_ZNODE+Constants.ZNODE_PATH_SEPERATOR+applicationName+","+instanceName+Constants.ZNODE_PATH_SEPERATOR+property.getKey(), 
				property.getValue().getBytes(), 
				zk.exists(Constants.SOURCE_ZNODE+Constants.ZNODE_PATH_SEPERATOR+applicationName+","+instanceName+Constants.ZNODE_PATH_SEPERATOR+property.getKey(),true).getVersion()
				);
		return (applicationName+","+instanceName+" updated successfully");
	}
	
	public String updateApplicationConfiguration(String applicationName,List<PropertyDto> properties) throws IOException, InterruptedException, KeeperException {
		ZooKeeper zk=conn.connect(host);
		for(PropertyDto property: properties)
			zk.setData(Constants.COMMON_PROPERTY_ZNODE+Constants.ZNODE_PATH_SEPERATOR+property.getKey(), 
					property.getValue().getBytes(), 
					zk.exists(Constants.COMMON_PROPERTY_ZNODE+Constants.ZNODE_PATH_SEPERATOR+property.getKey(),true).getVersion()
					);
		return (applicationName+" updated successfully");
	
	}*/
}

