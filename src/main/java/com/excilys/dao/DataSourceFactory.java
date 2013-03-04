package com.excilys.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSourceFactory {
	
	@Autowired
	private DataSource ds;
	private ThreadLocal<Connection> conn;
	
	public DataSourceFactory() {
		conn = new ThreadLocal<Connection>();
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
