package com.tool.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * �ػ�����
 * @author D-xxm
 */
public class DbCon {
    private static String className = null;
    private static String url = null;
    private static String user = null;
    private static String password = null;

    private static int minCon = 3; // ��С������
    private static List<Connection> listCon = new Vector<Connection>(); // ���ӳ�
	public static Logger logger = Logger.getLogger(DbCon.class);

    /**
     * ��ʼ�����ӳ�
     */
    static {
        loadProperties();
        try {
            for (int i = 0; i < minCon; i++) {
                listCon.add(getCon());
            }
        } catch (SQLException e) {
        	  logger.warn("---------------Initialize The Resource Failed!---------------");
  			e.printStackTrace();
			logger.error("Exception:"+e);
        }
        logger.warn("---------------Initial"+listCon.size()+"Resources!---------------");
    }

    /**
     * ��ȡ��Դ�ļ����á���������
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void loadProperties() {
        ResourceBundle rb;
        try {
        	
        	InputStream inputStream = DbCon.class.getResourceAsStream("/config/conn.properties");   
        	  Properties p = new Properties();   
        	  try {   
        	   p.load(inputStream);   
        	  } catch (IOException e1) {   
      			e1.printStackTrace();
    			logger.error("Exception:"+e1);
        	  }   
        	
        	
            className = p.getProperty("conn.driver");
            url = p.getProperty("conn.url");
            user = p.getProperty("conn.user");
            password = p.getProperty("conn.password");
            String smin = p.getProperty("conn.minCon");
            if ((smin != null) && (!("".equals(smin.trim()))))
                minCon = Integer.valueOf(smin).intValue();
            Class.forName(className);
        } catch (Exception e) {
            logger.error("---------------Exception When Reading The Resource Fils!--------------");
            className = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://localhost:3306/etlhelper";
            user = "root";
            password = "";
            try {
                Class.forName(className);
            } catch (Exception e2) {
                logger.error("---------------Exception When Loading The Driver!--------------");
    			e2.printStackTrace();
    			logger.error("Exception:"+e2);
            }
        }
    }

    /**
     * ��ȡ���ݿ����ӡ�����
     * @return
     * @throws SQLException
     */
    private static Connection getCon() throws SQLException {
        logger.info("---------------New DB Connection!---------------");
        if ((className == null) || (url == null) || (password==null))
            loadProperties();
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * �����ӳ�����ȡ���ӡ����û���ˡ����÷����½�����
     * @return
     * @throws SQLException*/
    public static Connection getConnection() throws SQLException {
        if ((listCon != null) && (listCon.size() > 0))
            return listCon.remove(listCon.size() - 1);
        return getCon();
    }

    /**
     * ������С�����������ʱ�������ӷ������ӳء�����ر����ݿ�����
     * @param con
     * @throws SQLException
     */
    public static void closeConnection(Connection conn) throws SQLException {
        if ((conn != null) && (!(conn.isClosed())))
            if ((listCon != null) && (listCon.size() < minCon))
                listCon.add(conn);
            else
                conn.close();
    }


}