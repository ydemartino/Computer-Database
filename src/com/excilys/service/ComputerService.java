package com.excilys.service;

import java.util.List;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;

public interface ComputerService {
	
	Company getCompany(int id);
	
	List<Company> getCompanies();
	
	Computer getComputer(int id);
	
	int getComputersCount();
	
	List<Computer> getComputers(int page, ComputerColumnSorter sorter);
	
	int getComputersCount(String filtre);
	
	List<Computer> getComputers(String filtre, int page, ComputerColumnSorter sorter);
	
	void saveOrUpdate(Computer computer);
	
	void deleteComputer(int id);

}
