package com.excilys.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.model.Company;

@Repository
public class DBCompanyDAO implements CompanyDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Company getCompany(int id) {
		return (Company)sessionFactory.getCurrentSession().get(Company.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getCompanies() {
		return sessionFactory.getCurrentSession().createCriteria(Company.class)
				.addOrder(Order.asc("name")).list();
	}

}
