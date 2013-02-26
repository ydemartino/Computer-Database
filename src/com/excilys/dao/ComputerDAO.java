package com.excilys.dao;

import java.util.List;

import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;

public interface ComputerDAO {
	
	int NB_PER_PAGE = 10;
	
	Computer getComputer(int id);
	
	int getComputersCount();
	
	List<Computer> getComputers();
	
	List<Computer> getComputers(int page, ComputerColumnSorter sorter);
	
	int getComputersCount(String filtre);
	
	List<Computer> getComputers(String filtre, ComputerColumnSorter sorter);
	
	List<Computer> getComputers(String filtre, int page, ComputerColumnSorter sorter);
	
	void saveOrUpdate(Computer computer);
	
	void delete(int id);

}
