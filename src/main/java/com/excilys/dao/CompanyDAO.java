package com.excilys.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.model.Company;

public interface CompanyDAO {

	List<Company> getCompanies() throws SQLException;
	
	Company getCompany(int id) throws SQLException;

}
