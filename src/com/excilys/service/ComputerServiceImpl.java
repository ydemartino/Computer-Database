package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.DBCompanyDAO;
import com.excilys.dao.DBComputerDAO;
import com.excilys.dao.DataBaseUtil;
import com.excilys.dao.DBStatisticDAO;
import com.excilys.dao.StatisticDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;
import com.excilys.model.Statistic;

public class ComputerServiceImpl implements ComputerService {
	
	private Connection connection;
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	private StatisticDAO statisticDAO;
	
	public ComputerServiceImpl() {
		connection = DataBaseUtil.getConnection();
		if (connection == null)
			throw new IllegalStateException("Impossible de récupérer une connexion !");
		computerDAO = new DBComputerDAO(connection);
		companyDAO = new DBCompanyDAO(connection);
		statisticDAO = new DBStatisticDAO(connection); 
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Company getCompany(int id) {
		try {
			return companyDAO.getCompany(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Company> getCompanies() {
		try {
			return companyDAO.getCompanies();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Computer getComputer(int id) {
		try {
			return computerDAO.getComputer(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getComputersCount() {
		try {
			return computerDAO.getComputersCount();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Computer> getComputers(int page, ComputerColumnSorter sorter) {
		try {
			return computerDAO.getComputers(page, sorter);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getComputersCount(String filtre) {
		try {
			return computerDAO.getComputersCount(filtre);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Computer> getComputers(String filtre, int page, ComputerColumnSorter sorter) {
		try {
			return computerDAO.getComputers(filtre, page, sorter);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void saveOrUpdate(Computer computer, String ipAddress) {
		try {
			connection.setAutoCommit(false);
			boolean inserted = computerDAO.saveOrUpdate(computer);
			Statistic stat = new Statistic();
			stat.setComputerId(computer.getId());
			stat.setDate(new Date());
			stat.setIp(ipAddress);
			stat.setOperation(inserted ? Statistic.DBOperation.CREATE : Statistic.DBOperation.UPDATE);
			statisticDAO.save(stat);
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			closeConnection();
		}
	}

	@Override
	public void deleteComputer(int id, String ipAddress) {
		try {
			connection.setAutoCommit(false);
			computerDAO.delete(id);
			Statistic stat = new Statistic();
			stat.setComputerId(id);
			stat.setDate(new Date());
			stat.setIp(ipAddress);
			stat.setOperation(Statistic.DBOperation.DELETE);
			statisticDAO.save(stat);
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

}
