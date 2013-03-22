package com.excilys.webservice;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.model.ComputerList;
import com.excilys.service.ComputerService;

public class ComputerRestServiceImpl implements ComputerRestService {

	@Autowired
	private ComputerService service;

	@Override
	public ComputerList getComputers(String filtre) {
		return new ComputerList(service.getComputers(filtre, null));
	}

	@Override
	public ComputerList getComputersByCompany(String companyFiltre) {
		return new ComputerList(service.getComputers(null, companyFiltre));
	}
	
	@Override
	public ComputerList getComputers(String filtre, String companyFiltre) {
		return new ComputerList(service.getComputers(filtre, companyFiltre));
	}


}
