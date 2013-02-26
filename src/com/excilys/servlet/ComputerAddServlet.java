package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.service.ComputerServiceImpl;
import com.excilys.validator.ComputerValidator;

/**
 * Servlet implementation class ComputerAddServlet
 */
@WebServlet("/ComputerAddServlet")
public class ComputerAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ComputerService service;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerAddServlet() {
        service = new ComputerServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = service.getCompanies();
		request.setAttribute("companies", companies);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerValidator validator = new ComputerValidator(request, service);
		if (validator.isValid()) {
			Computer c = validator.getComputer();
			service.saveOrUpdate(c);
			response.sendRedirect("ComputerServlet");
		} 
		else {
			request.setAttribute("validator", validator);
			doGet(request, response);
		}
	}

}
