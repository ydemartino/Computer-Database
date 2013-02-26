package com.excilys.service;

import java.util.List;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public interface ComputerService {
	
	Company getCompany(int id);
	
	List<Company> getCompanies();
	
	Computer getComputer(int id);
	
	int getComputersCount();
	
	List<Computer> getComputers(int page);
	
	int getComputersCount(String filtre);
	
	List<Computer> getComputers(String filtre, int page);
	
	void saveOrUpdate(Computer computer);

}
