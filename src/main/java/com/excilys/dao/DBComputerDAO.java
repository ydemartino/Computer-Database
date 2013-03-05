package com.excilys.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;

@Repository
public class DBComputerDAO implements ComputerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Criteria addOrderBy(Criteria crit, ComputerColumnSorter sorter) {
		if (sorter.isAsc())
			crit.addOrder(Order.asc(sorter.getColumnName()).ignoreCase());
		else 
			crit.addOrder(Order.desc(sorter.getColumnName()).ignoreCase());
		return crit;
	}
	
	private Criteria getDefaultJoinCriteria() {
		return sessionFactory.getCurrentSession()
				.createCriteria(Computer.class, "c")
				.createAlias("company", "cy", JoinType.LEFT_OUTER_JOIN);
	}

	@Override
	public Computer getComputer(int id) {
		return (Computer) sessionFactory.getCurrentSession().get(
				Computer.class, id);
	}

	@Override
	public int getComputersCount() {
		return ((Long) sessionFactory.getCurrentSession()
				.createCriteria(Computer.class)
				.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Computer> getComputers() {
		return sessionFactory.getCurrentSession()
				.createCriteria(Computer.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Computer> getComputers(int page, ComputerColumnSorter sorter) {
		return addOrderBy(getDefaultJoinCriteria(), sorter).setFirstResult(page * NB_PER_PAGE)
				.setMaxResults(NB_PER_PAGE)
				.list();
	}

	@Override
	public int getComputersCount(String filtre) {
		return ((Long) sessionFactory.getCurrentSession()
			.createCriteria(Computer.class)
			.add(Restrictions.like("name", String.format("%%%s%%", filtre)).ignoreCase())
			.setProjection(Projections.rowCount()).uniqueResult())
			.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Computer> getComputers(String filtre,
			ComputerColumnSorter sorter) {
		return addOrderBy(getDefaultJoinCriteria(), sorter)
				.add(Restrictions.like("name", String.format("%%%s%%", filtre)).ignoreCase())
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Computer> getComputers(String filtre, int page,
			ComputerColumnSorter sorter) {
		return addOrderBy(getDefaultJoinCriteria(), sorter)
				.add(Restrictions.like("name", String.format("%%%s%%", filtre)).ignoreCase())
				.setFirstResult(page * NB_PER_PAGE)
				.setMaxResults(NB_PER_PAGE)
				.list();
	}

	@Override
	public boolean saveOrUpdate(Computer computer) {
		boolean inserted = computer.getId() <= 0;
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(computer);
		return inserted;
	}

	@Override
	public void delete(int id) {
		Computer c = getComputer(id);
		Session session = sessionFactory.getCurrentSession();
		session.delete(c);
	}

}
