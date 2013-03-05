package com.excilys.repositories;

import org.springframework.data.repository.Repository;

import com.excilys.model.Statistic;

public interface StatisticRepository extends Repository<Statistic, Integer> {
	  Statistic save(Statistic entity);
}
