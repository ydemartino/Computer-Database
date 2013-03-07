package com.excilys.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.excilys.model.Computer;
import com.excilys.service.ComputerService;

public class ComputerValidator {

	private Computer computer;
	private boolean introducedFailed;
	private boolean discontinuedFailed;

	public ComputerValidator(Integer id, String name, String introduced,
			String discontinued, Integer companyId, ComputerService service) {
		computer = new Computer();

		if (id != null) {
			computer.setId(id);
		}

		if (name != null && name.trim().length() > 0) {
			computer.setName(name);
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (introduced != null && introduced.trim().length() > 0) {
			try {
				computer.setIntroduced(format.parse(introduced));
			} catch (ParseException e) {
				introducedFailed = true;
			}
		}

		if (discontinued != null && discontinued.trim().length() > 0) {
			try {
				computer.setDiscontinued(format.parse(discontinued));
			} catch (ParseException e) {
				discontinuedFailed = true;
			}
		}

		if (companyId != null)
			computer.setCompany(service.getCompany(companyId));
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
