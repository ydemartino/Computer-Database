package com.excilys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;

public interface ComputerService {
	
	Company getCompany(int id);
	
	List<Company> getCompanies();
	
	Computer getComputer(int id);
	
	List<Computer> getComputers(String filtre, String companyFiltre);
	
	Page<Computer> getComputers(Pageable page, ComputerColumnSorter sorter);
	
	Page<Computer> getComputers(String filtre, String companyFiltre, Pageable page, ComputerColumnSorter sorter);
	
	void saveOrUpdate(Computer computer, String ipAddress);
	
	void deleteComputer(int id, String ipAddress);

}
