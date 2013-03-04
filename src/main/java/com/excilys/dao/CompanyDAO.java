package com.excilys.dao;

import java.util.List;

import com.excilys.model.Company;

public interface CompanyDAO {

	List<Company> getCompanies();
	
	Company getCompany(int id);

}
