package com.cspinformatique.commons.property;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public abstract class PropertyUtil {
	private static HashMap<String, Properties> loadedProperties = new HashMap<>();
	
	public static String getPropert(String propertyName){
		return PropertyUtil.getProperty(propertyName, null);
	}
	
	public static String getProperty(String propertyName, String propertyFile){
		try{
			// Check for any docker variable.
			String propertyValue = System.getenv("DOCKER_" + propertyName);
			
			if(propertyValue == null){
				// Check for runtime property.
				propertyValue = System.getProperty(propertyName);
				
				if(propertyValue == null && propertyFile != null){
					Properties properties = PropertyUtil.loadedProperties.get(propertyFile);
					
					if(properties == null){
						properties = new Properties();
						properties.load(PropertyUtil.class.getClassLoader().getResourceAsStream(propertyFile));						
						
						PropertyUtil.loadedProperties.put(propertyFile, properties);
					}

					propertyValue = properties.getProperty(propertyName);
				}
			}
			
			return propertyValue;
		}catch(IOException ioEx){
			throw new RuntimeException(ioEx);
		}
	}
}