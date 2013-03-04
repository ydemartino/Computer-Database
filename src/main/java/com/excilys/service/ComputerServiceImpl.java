package com.excilys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.StatisticDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;
import com.excilys.model.ResultComputer;
import com.excilys.model.Statistic;

@Service
public class ComputerServiceImpl implements ComputerService {
	
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private StatisticDAO statisticDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Company getCompany(int id) {
		return companyDAO.getCompany(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Company> getCompanies() {
		return companyDAO.getCompanies();
	}

	@Override
	@Transactional(readOnly = true)
	public Computer getComputer(int id) {
		return computerDAO.getComputer(id);
	}

	@Override
	@Transactional(readOnly = true)
	public ResultComputer getComputers(int page, ComputerColumnSorter sorter) {
		List<Computer> computers = computerDAO.getComputers(page, sorter);
		int total = computerDAO.getComputersCount();
		return new ResultComputer(computers, total);
	}

	@Override
	@Transactional(readOnly = true)
	public ResultComputer getComputers(String filtre, int page, ComputerColumnSorter sorter) {
		List<Computer> computers = computerDAO.getComputers(filtre, page, sorter);
		int total = computerDAO.getComputersCount(filtre); 
		return new ResultComputer(computers, total);
	}

	@Override
	@Transactional
	public void saveOrUpdate(Computer computer, String ipAddress) {
		boolean inserted = computerDAO.saveOrUpdate(computer);
		Statistic stat = new Statistic();
		stat.setComputerId(computer.getId());
		stat.setDate(new Date());
		stat.setIp(ipAddress);
		stat.setOperation(inserted ? Statistic.DBOperation.CREATE : Statistic.DBOperation.UPDATE);
		statisticDAO.save(stat);
	}
	
	@Transactional
	@Override
	public void deleteComputer(int id, String ipAddress) {
		computerDAO.delete(id);
		Statistic stat = new Statistic();
		stat.setComputerId(id);
		stat.setDate(new Date());
		stat.setIp(ipAddress);
		stat.setOperation(Statistic.DBOperation.DELETE);
		statisticDAO.save(stat);
	}

}
