package com.tool.log4j;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class Log4jTest {


	public static Logger logger = Logger.getLogger(Log4jTest.class);

	
	
	
	@Before
	public void initial(){
		Log4JForTest.initial();
	}
	
	@Test
	public void getUc4JobTasks() throws SQLException {
			logger.info("..");
	}

}
