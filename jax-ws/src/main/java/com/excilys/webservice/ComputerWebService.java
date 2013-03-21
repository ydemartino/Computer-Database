package com.excilys.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.excilys.model.Computer;

@WebService
public interface ComputerWebService {
	List<Computer> getComputers(@WebParam(name="filtre") String filtre, @WebParam(name="companyFiltre") String companyFiltre);

}
