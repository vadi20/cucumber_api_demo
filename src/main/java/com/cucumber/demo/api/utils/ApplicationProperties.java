package com.cucumber.demo.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class ApplicationProperties {

	private static FileInputStream fis;
	private static Properties prop = null;
	private static final Logger logger = LogManager.getLogger(ApplicationProperties.class);

	public static String getProperty(String property) {		

		try {
			fis = new FileInputStream(new File("config.properties"));
			prop = new Properties();
			prop.load(fis);
		} catch(FileNotFoundException fnfe) {
			logger.error("Properties File Not Found", fnfe);
		} catch(IOException ioe) {
			logger.error("IO Exception while loading Properties File", ioe);
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				logger.error("IO Exception while closing file input stream", e);
			}
		}
		return prop.getProperty(property).trim();
	}
}
