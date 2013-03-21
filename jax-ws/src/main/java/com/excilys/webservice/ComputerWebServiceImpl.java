package com.excilys.webservice;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.model.Computer;
import com.excilys.service.ComputerService;

@WebService(endpointInterface = "com.excilys.webservice.ComputerWebService", serviceName = "computerWebService")
public class ComputerWebServiceImpl implements ComputerWebService {

	@Autowired
	private ComputerService service;
	
	@Override
	public List<Computer> getComputers(String filtre, String companyFiltre) {
		return service.getComputers(filtre, companyFiltre);
	}

}
