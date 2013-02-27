package com.excilys.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataBaseUtil {
	
	public static Connection getConnection() {
		try {
			InitialContext cxt = new InitialContext();
			DataSource ds = (DataSource)cxt.lookup("java:/comp/env/jdbc/Computer");
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
