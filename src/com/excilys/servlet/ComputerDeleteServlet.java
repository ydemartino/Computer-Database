package com.excilys.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.service.ComputerService;
import com.excilys.service.ComputerServiceImpl;

/**
 * Servlet implementation class ComputerDeleteServlet
 */
@WebServlet("/ComputerDeleteServlet")
public class ComputerDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ComputerService service;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerDeleteServlet() {
    	service = new ComputerServiceImpl();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		service.deleteComputer(id);
		response.sendRedirect("ComputerServlet");
	}

}
