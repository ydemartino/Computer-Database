package com.excilys.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.model.Statistic;

@Repository
public class DBStatisticDAO implements StatisticDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Statistic stat) {
		Session session = sessionFactory.getCurrentSession();
		session.save(stat);
	}

}
