package com.excilys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.dao.ComputerDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;
import com.excilys.model.ResultComputer;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@Controller
@RequestMapping("/computers")
public class ComputerController {

	@Autowired
	private ComputerService service;

	@RequestMapping(method = RequestMethod.GET)
	public String get(
			@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "sort", required = false, defaultValue = "1") Integer sort,
			Model model) {
		ComputerColumnSorter sorter = new ComputerColumnSorter(sort);
		model.addAttribute("sorter", sorter);

		ResultComputer res = filter == null ? service
				.getComputers(page, sorter) : service.getComputers(filter,
				page, sorter);

		model.addAttribute("computers", res.getComputers());
		model.addAttribute("total", res.getTotal());
		model.addAttribute("page", page);
		model.addAttribute("numMax",
				Math.min(res.getTotal(), (page + 1) * ComputerDAO.NB_PER_PAGE));
		model.addAttribute("nbPerPage", ComputerDAO.NB_PER_PAGE);

		return "listComputers";
	}

	@RequestMapping(value = "/new")
	public String add(@RequestParam(value="name", required=false) String name,
			@RequestParam(value="introduced", required=false) String introduced,
			@RequestParam(value="discontinued", required=false) String discontinued,
			@RequestParam(value="company", required=false) Integer companyId, Model model,
			HttpServletRequest request) {
		if (request.getMethod().equals("POST")) {
			ComputerValidator validator = new ComputerValidator(null, name,
					introduced, discontinued, companyId, service);
			
			if (validator.isValid()) { 
				Computer c = validator.getComputer();
				service.saveOrUpdate(c, request.getRemoteAddr());
				return String.format("redirect:/computers.do?added=%s", c.getName());
			} 
			model.addAttribute("validator", validator); 
		}
		 
		List<Company> companies = service.getCompanies();
		model.addAttribute("companies", companies);
		model.addAttribute("action", "/computers/new.do");
		return "formComputer";
	}

	@RequestMapping(value = "/{id}")
	public String edit(@PathVariable("id") Integer id,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="introduced", required=false) String introduced,
			@RequestParam(value="discontinued", required=false) String discontinued,
			@RequestParam(value="company", required=false) Integer companyId, Model model,
			HttpServletRequest request) {
		if (request.getMethod().equals("POST")) {
			ComputerValidator validator = new ComputerValidator(id, name,
					introduced, discontinued, companyId, service);
			
			if (validator.isValid()) { 
				Computer c = validator.getComputer();
				service.saveOrUpdate(c, request.getRemoteAddr());
				return String.format("redirect:/computers.do?edited=%s", c.getName());
			} 
			model.addAttribute("validator", validator); 
		} else {
			Computer computer = service.getComputer(id);
			request.setAttribute("computer", computer);
		}
	    
		request.setAttribute("isEdit", true);
		request.setAttribute("action", String.format("/computers/%d.do", id));
		List<Company> companies = service.getCompanies();
		request.setAttribute("companies", companies);
		model.addAttribute("id", id);

		return "formComputer";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable Integer id, Model model,
			HttpServletRequest request) {
		service.deleteComputer(id, request.getRemoteAddr());
		return "redirect:/computers.do";
	}
}
