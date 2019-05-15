package com.ubn.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class DbConnect {

	@Value("${DATASOURCE_JNDI}")
	private static String DATASOURCE_JNDI;
	
	static Logger Loger = LoggerFactory.getLogger(DbConnect.class);
	
	public static Connection getConn() {
		Context initCtx = null;
		DataSource ds = null;
		Connection conn = null;
		try {
			initCtx = new InitialContext();
			ds = (DataSource) initCtx.lookup(DATASOURCE_JNDI);

			conn = ds.getConnection();
			Loger.info("Connection " + conn);
		} catch (SQLException e) {
			Loger.info("SQLException In Connection : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Loger.info("Exception In connection : " + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getConn1() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@//10.8.64.72:1521/ubsoct24", "devops25", "devops25");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
