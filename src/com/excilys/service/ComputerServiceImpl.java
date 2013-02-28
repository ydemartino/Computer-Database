package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.DBCompanyDAO;
import com.excilys.dao.DBComputerDAO;
import com.excilys.dao.DataSourceFactory;
import com.excilys.dao.DBStatisticDAO;
import com.excilys.dao.StatisticDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;
import com.excilys.model.ResultComputer;
import com.excilys.model.Statistic;

public enum ComputerServiceImpl implements ComputerService {
	
	INSTANCE;
	
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	private StatisticDAO statisticDAO;
	
	private ComputerServiceImpl() {
		computerDAO = DBComputerDAO.INSTANCE;
		companyDAO = DBCompanyDAO.INSTANCE;
		statisticDAO = DBStatisticDAO.INSTANCE;
	}
	
	private void closeConnection() {
		DataSourceFactory.INSTANCE.closeConnection();
	}

	@Override
	public Company getCompany(int id) {
		try {
			return companyDAO.getCompany(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection();
		}
	}

	@Override
	public List<Company> getCompanies() {
		try {
			return companyDAO.getCompanies();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection();
		}
	}

	@Override
	public Computer getComputer(int id) {
		try {
			return computerDAO.getComputer(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection();
		}
	}

	@Override
	public ResultComputer getComputers(int page, ComputerColumnSorter sorter) {
		try {
			List<Computer> computers = computerDAO.getComputers(page, sorter);
			int total = computerDAO.getComputersCount(); 
			return new ResultComputer(computers, total);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection();
		}
	}

	@Override
	public ResultComputer getComputers(String filtre, int page, ComputerColumnSorter sorter) {
		try {
			List<Computer> computers = computerDAO.getComputers(filtre, page, sorter);
			int total = computerDAO.getComputersCount(filtre); 
			return new ResultComputer(computers, total);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection();
		}
	}

	@Override
	public void saveOrUpdate(Computer computer, String ipAddress) {
		Connection connection = null;
		try {
			connection = DataSourceFactory.INSTANCE.getConnectionThread();
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
				if (connection != null)
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
		Connection connection = null;
		try {
			connection = DataSourceFactory.INSTANCE.getConnectionThread();
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
