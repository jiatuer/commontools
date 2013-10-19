package com.tool.log4j;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class Log4JForTest {
   

	public static void initial(){
		Properties prop = new Properties();
	
		prop.setProperty("log4j.rootLogger", "DEBUG, CONSOLE");
		prop.setProperty("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender");
		prop.setProperty("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.CONSOLE.layout.ConversionPattern", "%d{HH:mm:ss,SSS} [%t] %-5p %C{1} : %m%n");
	
		PropertyConfigurator.configure(prop);
	}
}
