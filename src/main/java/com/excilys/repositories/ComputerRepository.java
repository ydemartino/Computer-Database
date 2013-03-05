package com.excilys.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.excilys.model.Computer;

public interface ComputerRepository extends Repository<Computer, Integer> {
	
	Computer findOne(Integer primaryKey);
	List<Computer> findAll();
	Page<Computer> findAll(Pageable pageable);
	List<Computer> findAllByNameLikeIgnoreCase(String filtre);
	Page<Computer> findAllByNameLikeIgnoreCase(String filtre, Pageable pageable);
    void delete(Computer entity);
    Computer save(Computer entity);

}
