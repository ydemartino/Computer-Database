package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.excilys.model.Statistic;

public enum DBStatisticDAO implements StatisticDAO {
	
	INSTANCE;

	@Override
	public void save(Statistic stat) throws SQLException {
		String query = String.format("INSERT INTO statistic (computer_id, date_modif, ip_address, operation) VALUES (%s, CURRENT_TIMESTAMP, ?, ?)",
				stat.getComputerId() <= 0 ? "COMPUTER_SEQ.CURRVAL" : "?");
		Connection connection = DataSourceFactory.INSTANCE.getConnectionThread();
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
