package com.wissen.urlShortner.configurationService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
	
	public void generateApplicationConfiguration(String propertyFile) {
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
						zk.create(Constants.SOURCE_ZNODE+znode, null,ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
				List<PropertyDto> properties = new ArrayList<>();
				List<String> propertiesFromFile = Files.readAllLines(Paths.get(propertyFilePaths.get(counter)));
				for (String property : propertiesFromFile) {
					properties.add(new PropertyDto(property.split("=")[0],property.split("=")[1]));
					if(propertyFileName.contains(",")) {
						zk.create(Constants.SOURCE_ZNODE+znode+Constants.ZNODE_PATH_SEPERATOR+property.split("=")[0],(property.split("=")[1]).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
					else
					{
						zk.create(Constants.COMMON_PROPERTY_ZNODE+property.split("=")[0],(property.split("=")[1]).getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}	
				}counter++;
			}
		}
		
		 catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
