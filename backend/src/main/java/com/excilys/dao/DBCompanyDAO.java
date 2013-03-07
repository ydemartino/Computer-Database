package com.excilys.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.excilys.model.Company;
import com.excilys.repositories.CompanyRepository;

@Repository
public class DBCompanyDAO implements CompanyDAO {

	@Autowired
	private CompanyRepository repo;
	
	@Override
	public Company getCompany(int id) {
		return repo.findOne(id);
	}

	@Override
	public List<Company> getCompanies() {
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		return repo.findAll(sort);
	}

}
