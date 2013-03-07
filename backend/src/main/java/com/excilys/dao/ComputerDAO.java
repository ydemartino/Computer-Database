package com.excilys.dao;

import java.util.List;

import org.springframework.data.domain.Page;

import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;

public interface ComputerDAO {
	
	int NB_PER_PAGE = 10;
	
	Computer getComputer(int id);
	
	List<Computer> getComputers();
	
	Page<Computer> getComputers(int page, ComputerColumnSorter sorter);
	
	Page<Computer> getComputers(String filtre, int page, ComputerColumnSorter sorter);
	
	boolean saveOrUpdate(Computer computer);
	
	void delete(int id);

}
