package com.excilys.service;

import java.util.List;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;
import com.excilys.model.ResultComputer;

public interface ComputerService {
	
	Company getCompany(int id);
	
	List<Company> getCompanies();
	
	Computer getComputer(int id);
	
	ResultComputer getComputers(int page, ComputerColumnSorter sorter);
	
	ResultComputer getComputers(String filtre, int page, ComputerColumnSorter sorter);
	
	void saveOrUpdate(Computer computer, String ipAddress);
	
	void deleteComputer(int id, String ipAddress);

}
