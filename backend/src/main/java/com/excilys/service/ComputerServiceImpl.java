package com.excilys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.StatisticDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;
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
		Assert.isTrue(id > 0);
		Company c = companyDAO.getCompany(id);
		Assert.notNull(c);
		return c;
	}

	@Override
	public List<Company> getCompanies() {
		return companyDAO.getCompanies();
	}

	@Override
	@Transactional(readOnly = true)
	public Computer getComputer(int id) {
		Assert.isTrue(id > 0);
		Computer c = computerDAO.getComputer(id);
		Assert.notNull(c);
		return c;
	}

	@Override
	public List<Computer> getComputers(String filtre, String companyFiltre) {
		return computerDAO.getComputers(filtre, companyFiltre); 
	}

	@Override
	public Page<Computer> getComputers(Pageable page, ComputerColumnSorter sorter) {
		return computerDAO.getComputers(page, sorter);
	}

	@Override
	public Page<Computer> getComputers(String filtre, String companyFiltre, Pageable page, ComputerColumnSorter sorter) {
		return computerDAO.getComputers(filtre, companyFiltre, page, sorter); 
	}

	@Override
	@Transactional
	public void saveOrUpdate(Computer computer, String ipAddress) {
		Assert.notNull(computer);
		Assert.notNull(computer.getName());
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
		Assert.isTrue(id > 0);
		computerDAO.delete(id);
		Statistic stat = new Statistic();
		stat.setComputerId(id);
		stat.setDate(new Date());
		stat.setIp(ipAddress);
		stat.setOperation(Statistic.DBOperation.DELETE);
		statisticDAO.save(stat);
	}
}
