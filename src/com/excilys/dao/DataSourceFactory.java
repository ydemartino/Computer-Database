package com.excilys.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public enum DataSourceFactory {
	
	INSTANCE;
	
	private DataSource ds;
	private ThreadLocal<Connection> conn;
	
	private DataSourceFactory() {
		conn = new ThreadLocal<Connection>();
		try {
			InitialContext cxt = new InitialContext();
			ds = (DataSource)cxt.lookup("java:/comp/env/jdbc/Computer");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnectionThread() throws SQLException {
		Connection c = conn.get();
		if (c == null)
			conn.set(c = ds.getConnection());
		return c;
	}
	
	public void closeConnection() {
		try {
			conn.get().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.remove();
	}

}
