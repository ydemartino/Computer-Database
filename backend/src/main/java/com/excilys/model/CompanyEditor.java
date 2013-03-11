package com.excilys.model;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.excilys.service.ComputerService;

@Configurable
public class CompanyEditor extends PropertyEditorSupport {
	
	@Autowired
	private ComputerService service;
	
	public CompanyEditor(ComputerService service) {
		this.service = service;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.length() <= 0)
			setValue(null);
		else {
			int id = Integer.parseInt(text);
			setValue(service.getCompany(id));
		}
	}
	
	@Override
	public String getAsText() {
		Company company = (Company) getValue();
		if (company == null)
			return null;
		return String.valueOf(company.getId());
	}

}
