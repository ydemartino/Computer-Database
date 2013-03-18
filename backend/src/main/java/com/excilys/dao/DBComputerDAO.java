package com.excilys.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;
import com.excilys.model.QCompany;
import com.excilys.model.QComputer;
import com.excilys.repositories.ComputerRepository;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;


@Repository
public class DBComputerDAO implements ComputerDAO {

	@Autowired
	private ComputerRepository repo;
	
	@PersistenceContext
	private EntityManager em;

	private Sort getSort(ComputerColumnSorter sorter) {
		return new Sort(sorter.isAsc() ? Sort.Direction.ASC
				: Sort.Direction.DESC, sorter.getColumnName());
	}

	@Override
	public Computer getComputer(int id) {
		return repo.findOne(id);
	}

	@Override
	public List<Computer> getComputers() {
		return repo.findAll();
	}

	@Override
	public Page<Computer> getComputers(int page, ComputerColumnSorter sorter,
			int nbPerPage) {
		return getComputers(null, null, page, sorter, nbPerPage);
	}

	@Override
	public Page<Computer> getComputers(String filtre, String companyFiltre, int page,
			ComputerColumnSorter sorter, int nbPerPage) {
		Pageable pageable = new PageRequest(page, nbPerPage, getSort(sorter));
		BooleanBuilder bb = new BooleanBuilder();
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		if (filtre != null && filtre.length() > 0) 
			bb.and(computer.name.containsIgnoreCase(filtre));
		if (companyFiltre != null && companyFiltre.length() > 0)
			bb.and(company.name.containsIgnoreCase(companyFiltre));
		JPAQuery query = new JPAQuery(em)
			.from(computer)
			.leftJoin(computer.company, company)
			.where(bb);
		long total = query.count();
		query.fetch()
			.orderBy(sorter.getOrderSpecifier())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());
		List<Computer> computers = query.list(computer);
		return new PageImpl<Computer>(computers, pageable, total);
	}

	@Override
	public boolean saveOrUpdate(Computer computer) {
		boolean inserted = computer.getId() <= 0;
		repo.save(computer);
		return inserted;
	}

	@Override
	public void delete(int id) {
		Computer c = getComputer(id);
		repo.delete(c);
	}

}
