package com.excilys.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.model.Statistic;

@Repository
public class DBStatisticDAO implements StatisticDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void save(Statistic stat) {
		String query = String.format("INSERT INTO statistic (computer_id, date_modif, ip_address, operation) VALUES (%s, CURRENT_TIMESTAMP, :ip, :operation)",
				stat.getComputerId() <= 0 ? "COMPUTER_SEQ.CURRVAL" : ":id");

		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		if (stat.getComputerId() > 0) {
			namedParams.addValue("id", stat.getComputerId());
		}
		namedParams.addValue("ip", stat.getIp());
		namedParams.addValue("operation", stat.getOperation().getNum());
		new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource()).update(query, namedParams);
	}

}
