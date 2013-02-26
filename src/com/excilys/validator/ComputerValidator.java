package com.excilys.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import com.excilys.model.Computer;
import com.excilys.service.ComputerService;

public class ComputerValidator {

	private Computer computer;
	private boolean introducedFailed;
	private boolean discontinuedFailed;

	public ComputerValidator(HttpServletRequest request, ComputerService service) {
		computer = new Computer();

		if (request.getParameter("id") != null) {
			computer.setId(Integer.parseInt(request.getParameter("id")));
		}

		if (request.getParameter("name") != null
				&& request.getParameter("name").trim().length() > 0) {
			computer.setName(request.getParameter("name"));
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (request.getParameter("introduced") != null
				&& request.getParameter("introduced").trim().length() > 0) {
			try {
				computer.setIntroduced(format.parse(request
						.getParameter("introduced")));
			} catch (ParseException e) {
				// e.printStackTrace();
				introducedFailed = true;
			}
		}

		if (request.getParameter("discontinued") != null
				&& request.getParameter("discontinued").trim().length() > 0) {
			try {
				computer.setDiscontinued(format.parse(request
						.getParameter("discontinued")));
			} catch (ParseException e) {
				// e.printStackTrace();
				discontinuedFailed = true;
			}
		}

		try {
			int companyId = Integer.parseInt(request.getParameter("company"));
			computer.setCompany(service.getCompany(companyId));
		} catch (NumberFormatException e) {

		}
	}

	public boolean isNameValid() {
		return computer.getName() != null;
	}

	public boolean isIntroducedValid() {
		return !introducedFailed;
	}

	public boolean isDiscontinuedValid() {
		return !discontinuedFailed;
	}

	public boolean isValid() {
		return isNameValid() && isIntroducedValid() && isDiscontinuedValid();
	}

	public Computer getComputer() {
		return computer;
	}

}
