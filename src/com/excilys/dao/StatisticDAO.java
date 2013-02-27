package com.excilys.dao;

import java.sql.SQLException;

import com.excilys.model.Statistic;

public interface StatisticDAO {
	
	void save(Statistic stat) throws SQLException;

}
