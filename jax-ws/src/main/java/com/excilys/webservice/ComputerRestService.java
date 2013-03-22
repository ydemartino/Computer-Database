package com.excilys.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.excilys.model.ComputerList;

@Path("/computer/")
public interface ComputerRestService {
	
	@GET
	@Produces("application/xml")
	@Path("name/{filtre}")
	public ComputerList getComputers(@PathParam("filtre") String filtre);
	
	@GET
	@Produces("application/xml")
	@Path("company/{companyFiltre}")
	public ComputerList getComputersByCompany(@PathParam("companyFiltre") String companyFiltre);

	@GET
	@Produces("application/xml")
	@Path("{filtre}/company/{companyFiltre}")
	public ComputerList getComputers(@PathParam("filtre") String filtre, @PathParam("companyFiltre") String companyFiltre);
	
}
