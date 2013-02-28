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
 * Servlet implementation class ComputerEditServlet
 */
@WebServlet("/ComputerEditServlet")
public class ComputerEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ComputerService service;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerEditServlet() {
    	service = ComputerServiceImpl.INSTANCE;
    }
    
    private void initRequest(HttpServletRequest request, ComputerService service) {
		request.setAttribute("isEdit", true);
		request.setAttribute("action", "ComputerEditServlet?id=" + request.getParameter("id"));
		List<Company> companies = service.getCompanies();
		request.setAttribute("companies", companies);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initRequest(request, service);
		int id = Integer.parseInt(request.getParameter("id"));
		Computer computer = service.getComputer(id);
		request.setAttribute("computer", computer);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/formComputer.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerValidator validator = new ComputerValidator(request, service);
		if (validator.isValid()) {
			Computer c = validator.getComputer();
			service.saveOrUpdate(c, request.getRemoteAddr());
			response.sendRedirect("ComputerServlet?edited=" + c.getName());
		} 
		else {
			initRequest(request, service);
			request.setAttribute("validator", validator);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/formComputer.jsp");
			rd.forward(request, response);
		}
		
	}

}
