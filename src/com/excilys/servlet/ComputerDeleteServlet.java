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
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerDeleteServlet() {
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerService service = new ComputerServiceImpl();
		int id = Integer.parseInt(request.getParameter("id"));
		service.deleteComputer(id, request.getRemoteAddr());
		response.sendRedirect("ComputerServlet");
	}

}
