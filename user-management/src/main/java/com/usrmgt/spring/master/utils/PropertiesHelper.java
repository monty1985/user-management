package com.usrmgt.spring.master.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Logger;

import com.usrmgt.spring.master.exceptions.PropertyNotFound;

public class PropertiesHelper {

	private final static Logger LOGGER = Logger.getLogger(PropertiesHelper.class.getName());	
    
	/**
	 * This class stores Host properties.
	 */
	@SuppressWarnings("serial")
	public static class HostProperties extends Properties{
		private final Logger LOGGER = Logger.getLogger(PropertiesHelper.class.getName());	

		/**
		 * Gets the required property. If no property is found this method will throw an exception.
		 * Use this if the property is mandatory.
		 *
		 * @param  property - The Host property.
		 * @return The value of the Host property.
		 * @throws PropertyNotFound In case we do not find the property.
		 */
		public String getRequiredProperty(String property) throws PropertyNotFound{
			LOGGER.info("Looking up required property = [" + property + "] in runtime.properties");
			String value = super.getProperty(property);
			if( value == null){
				throw new PropertyNotFound("There is no property = [" + property + "] defined in the runtime.properties file.");
			}
			return value;
		}
	}

	/**
	 * Loads the Host properties object from the Host properties file.
	 *
	 * @param  HostFileName - The environment name or the folder path.
	 * @return The Host properties.
	 * @throws Exception
	 * @throws FileNotFoundException In case the environment properties file is not found.
	 */
	public static HostProperties loadTestProperties(String hostConfigFile) throws Exception,FileNotFoundException{
		LOGGER.info("Getting Property File for the Host: " + hostConfigFile);

		String configFilePath = UserUtils.getPathToTestResourcesDirectory() + File.separator +  hostConfigFile + ".properties";			

		if (hostConfigFile.contains(File.separator)){
			configFilePath = hostConfigFile;
		}

		HostProperties properties = new HostProperties();
		try {
			properties.load(new FileInputStream(configFilePath));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("The properties file for environment=" + "[" + hostConfigFile + "] does not exist: " + configFilePath );
		}

		LOGGER.info("Host Property File loaded successfully: " + hostConfigFile);
		return properties;
	}

	/**
	 * Load properties from specified properties file
	 *
	 * @param  propertiesFile - An File object of properties file.
	 * @return The Properties object
	 * @throws Exception
	 */
	public static Properties loadProperties(File propertiesFile) throws Exception {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFile));
		} catch(FileNotFoundException e) {
			throw new FileNotFoundException("The properties file does not exist: " + propertiesFile.getAbsolutePath());
		}
		return properties;
	}

	/**
	 * Prints key,value for all of the Host properties.
	 *
	 * @param  properties - The Host properties being print.
	 */
	public static void printProperties(HostProperties properties){
		LOGGER.info("\n------- Print Properties -----------");
		for(String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			LOGGER.info(key + " -> " + value);
		}
		LOGGER.info("\n------- End Properties -----------");
	}

	/**
	 * Get hosts number from the properties file
	 *
	 * @param properties - Host properties
	 * @return A list of Host numbers
	 */
	public static List<String> getHostsFromProperties(HostProperties properties){
		ArrayList<String> nodeNames = new ArrayList <String>();
		for(Entry<Object, Object> e : properties.entrySet()) {
			String keyName = e.getKey().toString();
			if(keyName.endsWith(".hostname")) {
				int dotIndex = keyName.indexOf('.');
				nodeNames.add(keyName.substring(0, dotIndex));
			}
		}
		return nodeNames;
	}

	/**
	 * Get host number whose name equals name parameter
	 *
	 * @param properties - Host properties
	 * @param name - The host name property value.
	 * @return Host number if found, else return null
	 */
	public static String getHostFromPropertiesByName(HostProperties properties, String name){
		List<String> hostNumbers = getHostsFromProperties(properties);
		for(String hostNumber: hostNumbers) {
			String nameValue = properties.getProperty(hostNumber + ".name");
			if(name.equalsIgnoreCase(nameValue)) {
				return hostNumber;
			}
		}
		return null;
	}

	/**
	 * Get host number list whose purpose equals purpose parameter
	 *
	 * @param properties - Host properties
	 * @param purpose - The Host purpose property value.
	 * @return Host number list if found, else return zero size list
	 */
	public static List<String> getHostFromPropertiesByPurpose(HostProperties properties, String purpose){
		List<String> listHost = new ArrayList<String>();
		List<String> hostNames = getHostsFromProperties(properties);
		for(String hostName : hostNames)
		{
			String[] purposeValues = properties.getProperty(hostName + ".purpose").split(",");
			List<String> listPurpose = Arrays.asList(purposeValues);
			if ( listPurpose.contains(purpose) )
				listHost.add(hostName);
		}

		return listHost;
	}
	
	public static List<String> getAccessList(HostProperties properties) {
		List<String> listAccess = new ArrayList<String>();
		List<String> hostNames = getHostsFromProperties(properties);
		for(String hostName : hostNames)
		{
			String[] accessValues = properties.getProperty(hostName + ".access").split(",");
			for (String accessVal : accessValues) {
				listAccess.add(accessVal.trim());
			}			
		}
		return listAccess;	
	}
	
	
	/**
	 * Get host number list whose purpose equals purpose parameter
	 *
	 * @param properties - Host properties
	 * @param purpose - The Host purpose property value.
	 * @return Host number list if found, else return zero size list
	 */
	public static List<String> getHostFromPropertiesByAccess(HostProperties properties, String access){
		List<String> listHost = new ArrayList<String>();
		List<String> hostNames = getHostsFromProperties(properties);
		for(String hostName : hostNames)
		{
			String[] accessValues = properties.getProperty(hostName + ".access").split(",");
			List<String> listPurpose = Arrays.asList(accessValues);
			if ( listPurpose.contains(access) )
				listHost.add(hostName);
		}
		return listHost;
	}


	/**
	 * Get component name list whose purpose equals purpose parameter from properties passed in.
	 *
	 * @param properties - The Host properties.
	 * @param purpose - The Host purpose.
	 * @return Component name list if found, else returns zero size list.
	 */
	public static List<String> getComponentsFromPropertiesByPurpose(HostProperties properties, String purpose){
		List<String> retCompNames = new ArrayList<String>();
		List<String> compNames = getComponentsFromProperties(properties);
		for(String compName : compNames) {
			String[] purposeValues = properties.getProperty(compName + ".purpose").split(",");
			List<String> listPurpose = Arrays.asList(purposeValues);
			if ( listPurpose.contains(purpose) )
				retCompNames.add(compName);
		}
		return retCompNames;
	}
	
	/**
	 * Get all component names from the properties file, each component must define "purpose" attribute.
	 *
	 * @param properties - Host properties
	 * @return A list of component names
	 */
	public static List<String> getComponentsFromProperties(HostProperties properties){
		List<String> compNames = new ArrayList <String>();
		for(Entry<Object, Object> e : properties.entrySet()) {
			String keyName = e.getKey().toString();
			if(keyName.endsWith(".purpose")) {
				int dotIndex = keyName.indexOf('.');
				compNames.add(keyName.substring(0, dotIndex));
			}
		}
		return compNames;
	}
	
	/**
	 * Get all properties by component name.
	 *
	 * @param properties - Host properties.
	 * @param name - The component name.
	 * @return {@link Properties}
	 */
	public static Properties getPropertiesByComponent(HostProperties properties, String compName) {
		Properties props = new Properties();
		for(Object keyObj: properties.keySet()) {
			String key = keyObj.toString();
			if(key.startsWith(compName + ".")) {
				props.setProperty(key, properties.getProperty(key));
			}
		}
		return props;
	}	
}
