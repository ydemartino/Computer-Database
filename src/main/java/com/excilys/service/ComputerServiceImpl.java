package com.excilys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@Transactional(readOnly = true)
public class ComputerServiceImpl implements ComputerService {
	
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private StatisticDAO statisticDAO;
	
	@Override
	public Company getCompany(int id) {
		return companyDAO.getCompany(id);
	}

	@Override
	public List<Company> getCompanies() {
		return companyDAO.getCompanies();
	}

	@Override
	@Transactional(readOnly = true)
	public Computer getComputer(int id) {
		return computerDAO.getComputer(id);
	}

	@Override
	public ResultComputer getComputers(int page, ComputerColumnSorter sorter) {
		Page<Computer> computers = computerDAO.getComputers(page, sorter);
		return new ResultComputer(computers.getContent(), (int)computers.getTotalElements());
	}

	@Override
	public ResultComputer getComputers(String filtre, int page, ComputerColumnSorter sorter) {
		Page<Computer> computers = computerDAO.getComputers(filtre, page, sorter); 
		return new ResultComputer(computers.getContent(), (int)computers.getTotalElements());
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
