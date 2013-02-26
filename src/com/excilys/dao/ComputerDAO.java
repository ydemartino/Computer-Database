package com.excilys.dao;

import java.util.List;

import com.excilys.model.Computer;

public interface ComputerDAO {
	
	int NB_PER_PAGE = 10;
	
	Computer getComputer(int id);
	
	int getComputersCount();
	
	List<Computer> getComputers();
	
	List<Computer> getComputers(int page);
	
	int getComputersCount(String filtre);
	
	List<Computer> getComputers(String filtre);
	
	List<Computer> getComputers(String filtre, int page);
	
	void saveOrUpdate(Computer computer);
	
	void delete(int id);

}
