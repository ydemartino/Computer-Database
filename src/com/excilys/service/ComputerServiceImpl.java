package com.excilys.service;

import java.util.List;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.DBCompanyDAO;
import com.excilys.dao.DBComputerDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerServiceImpl implements ComputerService {
	
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	
	public ComputerServiceImpl() {
		computerDAO = new DBComputerDAO();
		companyDAO = new DBCompanyDAO();
	}

	@Override
	public Company getCompany(int id) {
		return companyDAO.getCompany(id);
	}

	@Override
	public List<Company> getCompanies() {
		return companyDAO.getCompanies();
	}

	@Override
	public Computer getComputer(int id) {
		return computerDAO.getComputer(id);
	}

	@Override
	public int getComputersCount() {
		return computerDAO.getComputersCount();
	}

	@Override
	public List<Computer> getComputers(int page) {
		return computerDAO.getComputers(page);
	}

	@Override
	public int getComputersCount(String filtre) {
		return computerDAO.getComputersCount(filtre);
	}

	@Override
	public List<Computer> getComputers(String filtre, int page) {
		return computerDAO.getComputers(filtre, page);
	}

	@Override
	public void saveOrUpdate(Computer computer) {
		computerDAO.saveOrUpdate(computer);
	}

	@Override
	public void deleteComputer(int id) {
		computerDAO.delete(id);
	}

}
