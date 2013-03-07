package com.excilys.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

import com.excilys.model.Company;

public interface CompanyRepository extends Repository<Company, Integer> {
	List<Company> findAll(Sort sort);
	Company findOne(Integer primaryKey);
}
