package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dao.ComputerDAO;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.service.ComputerServiceImpl;

/**
 * Servlet implementation class ComputerServlet
 */
@WebServlet("/ComputerServlet")
public class ComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ComputerService service;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerServlet() {
        service = new ComputerServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 0;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) { 
			//e.printStackTrace();
		}
		
		final String filter = request.getParameter("filter");
		
		List<Computer> computers = null;
		int total;
		if (filter == null) {
			total = service.getComputersCount();
			computers = service.getComputers(page);
		}
		else {
			total = service.getComputersCount(filter);
			computers = service.getComputers(filter, page);
		}

		request.setAttribute("computers", computers);
		request.setAttribute("total", total);
		request.setAttribute("page", page);
		request.setAttribute("nbPerPage", ComputerDAO.NB_PER_PAGE);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/listComputers.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * /
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	 */

}
