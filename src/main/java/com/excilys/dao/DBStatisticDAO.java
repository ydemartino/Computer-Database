package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.model.Statistic;

@Repository
public class DBStatisticDAO implements StatisticDAO {
	
	@Autowired
	private DataSourceFactory dsFactory;
	
	@Override
	public void save(Statistic stat) throws SQLException {
		String query = String.format("INSERT INTO statistic (computer_id, date_modif, ip_address, operation) VALUES (%s, CURRENT_TIMESTAMP, ?, ?)",
				stat.getComputerId() <= 0 ? "COMPUTER_SEQ.CURRVAL" : "?");
		Connection connection = dsFactory.getConnectionThread();
		PreparedStatement stmt = connection.prepareStatement(query);
		int idx = 0;
		if (stat.getComputerId() > 0) {
			stmt.setInt(++idx, stat.getComputerId());
		}
		stmt.setString(++idx, stat.getIp());
		stmt.setInt(++idx, stat.getOperation().getNum());
		stmt.executeUpdate();
	}

}
