<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" --%>
<%--     pageEncoding="UTF-8"%> --%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="com.excilys.model.Computer"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Computers database</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value="/css/bootstrap.min.css"/>">
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value="/css/main.css"/>">
</head>
<% 
Integer nbPerPage = (Integer)request.getAttribute("nbPerPage");
Integer numPage = (Integer)request.getAttribute("page");
Integer total = (Integer)request.getAttribute("total");
String extraParam = request.getParameter("filter") != null ? String.format("&filter=%s", request.getParameter("filter")) : "";
boolean isLastPage = (numPage + 1) * nbPerPage > total;
%>
<body>
	<header class="topbar">
		<h1 class="fill">
			<a href="<c:url value="ComputerServlet"/>">Computer database &mdash;</a>
		</h1>
	</header>

	<section id="main">
		<h1><%= total %> computers found</h1>

		<div id="actions">
			<form action="" method="GET">
				<input type="search" id="searchbox" name="filter" value=""
					placeholder="Filter by computer name...">
				<input type="submit" id="searchsubmit" value="Filter by name"
					class="btn primary">
			</form>

			<a class="btn success" id="add" href="<c:url value="ComputerAddServlet"/>">Add a new
				computer</a>
		</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<th class="col2 header headerSortUp"><a href="/computers?s=-2">Computer
							name</a></th>
					<th class="col3 header "><a href="/computers?s=3">Introduced</a>
					</th>
					<th class="col4 header "><a href="/computers?s=4">Discontinued</a>
					</th>
					<th class="col5 header "><a href="/computers?s=5">Company</a>
					</th>
				</tr>
			</thead>
			<tbody>
<% 				DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
				for(Computer c : (List<Computer>)request.getAttribute("computers"))   { 	%>
				<tr>
					<td><a href="/computers/381"><%= c.getName() %></a></td>
					<td><em><% if (c.getIntroduced() == null) %>-<% else %><%= format.format(c.getIntroduced()) %></em></td>
					<td><em><% if (c.getDiscontinued() == null) %>-<% else %><%= format.format(c.getDiscontinued()) %></em></td>
					<td><em><% if (c.getCompany() == null) %>-<% else %><%= c.getCompany().getName() %></em></td>
				</tr>
				<% } %>
			</tbody>
		</table>
		
		<div id="pagination" class="pagination">
			<ul>
				<li class="prev<%= numPage <= 0 ? " disabled" : "" %>"><a<%= (numPage <= 0) ? "" : " href=\"ComputerServlet?page=" + (numPage - 1) + extraParam + "\"" %>>&larr; Previous</a></li>
				<li class="current"><a>Displaying <%= (numPage * nbPerPage + 1) %> to <%= Math.min(total.intValue(), ((numPage + 1) * nbPerPage)) %> of <%= total %></a></li>
				<li class="next<%= isLastPage ? " disabled" : "" %>"><a<%= (isLastPage) ? "" : " href=\"ComputerServlet?page=" + (numPage + 1) + extraParam + "\"" %>>Next &rarr;</a></li>
			</ul>
		</div>
	</section>
</body>
</html>
