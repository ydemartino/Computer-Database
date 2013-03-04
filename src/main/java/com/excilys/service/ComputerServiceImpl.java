package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.DataSourceFactory;
import com.excilys.dao.StatisticDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;
import com.excilys.model.ResultComputer;
import com.excilys.model.Statistic;

@Service
public class ComputerServiceImpl implements ComputerService {
	
	@Autowired
	private DataSourceFactory dsFactory;
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private StatisticDAO statisticDAO;
	
	private void closeConnection() {
		dsFactory.closeConnection();
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
			connection = dsFactory.getConnectionThread();
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
			connection = dsFactory.getConnectionThread();
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
