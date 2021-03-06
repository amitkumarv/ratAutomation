package com.icreon.res_allocqa.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtils {
	
	public static final String PROPERTY_BUILD_FILE = "D:\\workspace\\ratAutomation\\build.properties";
	
	public static String  getPropVal(String key) {
		String propValue = null;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(PROPERTY_BUILD_FILE));
			propValue = prop.getProperty(key);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return propValue;
	}
}
