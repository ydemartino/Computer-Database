package com.excilys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.dao.ComputerDAO;
import com.excilys.model.Company;
import com.excilys.model.CompanyEditor;
import com.excilys.model.Computer;
import com.excilys.model.ComputerColumnSorter;
import com.excilys.model.ResultComputer;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/computers")
public class ComputerController {

	@Autowired
	private ComputerService service;

	@RequestMapping(method = RequestMethod.GET)
	public String get(
			@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "sort", required = false, defaultValue = "1") int sort,
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

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(@ModelAttribute("computer") Computer computer, Model model) {
		List<Company> companies = service.getCompanies();
		model.addAttribute("companies", companies);
		model.addAttribute("action", "/computers/new.do");
		return "formComputer";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String addPost(@Valid @ModelAttribute("computer") Computer computer, BindingResult result, 
			Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			service.saveOrUpdate(computer, request.getRemoteAddr());
			redirectAttributes.addFlashAttribute("computer", computer.getName());
			redirectAttributes.addFlashAttribute("action", "added");
			return "redirect:/computers.do";
		}
		
		model.addAttribute("result", result);
		
		List<Company> companies = service.getCompanies();
		model.addAttribute("companies", companies);
		model.addAttribute("action", "/computers/new.do");
		return "formComputer";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id,
			Model model, HttpServletRequest request) {
		
		Computer computer = service.getComputer(id);
		request.setAttribute("computer", computer);
	    
		request.setAttribute("isEdit", true);
		request.setAttribute("action", String.format("/computers/%d.do", id));
		List<Company> companies = service.getCompanies();
		request.setAttribute("companies", companies);
		model.addAttribute("id", id);

		return "formComputer";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String edit2(@PathVariable("id") Integer id,
			@Valid @ModelAttribute("computer") Computer computer,
			BindingResult result, RedirectAttributes redirectAttributes,
			Model model, HttpServletRequest request) {
		if (!result.hasErrors()) {
			service.saveOrUpdate(computer, request.getRemoteAddr());
			redirectAttributes.addFlashAttribute("computer", computer.getName());
			redirectAttributes.addFlashAttribute("action", "edited");
			return "redirect:/computers.do";
		}

		model.addAttribute("result", result);
		
		request.setAttribute("isEdit", true);
		request.setAttribute("action", String.format("/computers/%d.do", id));
		List<Company> companies = service.getCompanies();
		request.setAttribute("companies", companies);
		model.addAttribute("id", id);

		return "formComputer";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable Integer id, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Computer c = service.getComputer(id);
		if (c != null) {
			redirectAttributes.addFlashAttribute("computer", c.getName());
			redirectAttributes.addFlashAttribute("action", "deleted");
			service.deleteComputer(id, request.getRemoteAddr());
		}
		return "redirect:/computers.do";
	}
	
	@InitBinder
	public void initBinderUser(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new CompanyEditor(service));
	}
}
