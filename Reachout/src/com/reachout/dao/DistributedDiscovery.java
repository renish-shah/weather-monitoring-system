/**
 * 
 */
package com.reachout.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author RENISH
 * 
 */
public class DistributedDiscovery {

	private static final String NODE_B_CHARITY = "nodeB_charity";
	private static final String NODE_A_CHARITY = "nodeA_charity";
	private static final String PROP_FILE = "config.properties";

	public static void main(String[] args) {

		DistributedDiscovery discovery = new DistributedDiscovery();
		discovery.ReadConfigFile();

	}

	public List<String> ReadConfigFile() {

		List<String> listOfRemoteCharities = null;
		try {
			InputStream is = DistributedDiscovery.class
					.getResourceAsStream(PROP_FILE);
			Properties prop = new Properties();
			prop.load(is);
			String nodeA_charity = prop.getProperty(NODE_A_CHARITY);
			String nodeB_charity = prop.getProperty(NODE_B_CHARITY);

			listOfRemoteCharities = new ArrayList<String>();
			listOfRemoteCharities.add(nodeA_charity);
			listOfRemoteCharities.add(nodeB_charity);

			is.close();

			System.out.println("Node A Path :" + nodeA_charity);
			System.out.println("Node B Path :" + nodeB_charity);
			/* code to use values read from the file */
		} catch (Exception e) {
			System.out.println("Failed to read from " + PROP_FILE + " file.");
		}
		return listOfRemoteCharities;

	}
}
