package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dao.ComputerDAO;
import com.excilys.model.ComputerColumnSorter;
import com.excilys.model.ResultComputer;
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
    	service = ComputerServiceImpl.INSTANCE;
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
		
		int sort = 1;
		try {
			sort = Integer.parseInt(request.getParameter("sort"));
		} catch (NumberFormatException e) { 
			//e.printStackTrace();
		}
		ComputerColumnSorter sorter = new ComputerColumnSorter(sort);
		request.setAttribute("sorter", sorter);
		
		final String filter = request.getParameter("filter");
		
		ResultComputer res = filter == null 
				? service.getComputers(page, sorter)
				: service.getComputers(filter, page, sorter);
		
		request.setAttribute("computers", res.getComputers());
		request.setAttribute("total", res.getTotal());
		request.setAttribute("page", page);
		request.setAttribute("numMax", Math.min(res.getTotal(), (page + 1) * ComputerDAO.NB_PER_PAGE));
		request.setAttribute("nbPerPage", ComputerDAO.NB_PER_PAGE);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/listComputers.jsp");
		rd.forward(request, response);
	}

}
