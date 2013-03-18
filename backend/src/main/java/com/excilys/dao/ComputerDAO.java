package com.excilys.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;

public interface ComputerDAO {
	
	Computer getComputer(int id);
	
	List<Computer> getComputers();
	
	Page<Computer> getComputers(Pageable page, ComputerColumnSorter sorter);
	
	Page<Computer> getComputers(String filtre, String companyFiltre, Pageable page, ComputerColumnSorter sorter);
	
	boolean saveOrUpdate(Computer computer);
	
	void delete(int id);

}
