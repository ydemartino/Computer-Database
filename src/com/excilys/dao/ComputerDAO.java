package com.excilys.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;

public interface ComputerDAO {
	
	int NB_PER_PAGE = 10;
	
	Computer getComputer(int id) throws SQLException;
	
	int getComputersCount() throws SQLException;
	
	List<Computer> getComputers() throws SQLException;
	
	List<Computer> getComputers(int page, ComputerColumnSorter sorter) throws SQLException;
	
	int getComputersCount(String filtre) throws SQLException;
	
	List<Computer> getComputers(String filtre, ComputerColumnSorter sorter) throws SQLException;
	
	List<Computer> getComputers(String filtre, int page, ComputerColumnSorter sorter) throws SQLException;
	
	boolean saveOrUpdate(Computer computer) throws SQLException;
	
	void delete(int id) throws SQLException;

}
