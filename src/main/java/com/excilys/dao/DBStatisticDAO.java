package com.excilys.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.model.Statistic;
import com.excilys.repositories.StatisticRepository;

@Repository
public class DBStatisticDAO implements StatisticDAO {
	
	@Autowired
	private StatisticRepository repo;
	
	@Override
	public void save(Statistic stat) {
		repo.save(stat);
	}

}
