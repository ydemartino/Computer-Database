<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" --%>
<%--     pageEncoding="UTF-8"%> --%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="com.excilys.model.Computer"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	Integer nbPerPage = (Integer) request.getAttribute("nbPerPage");
	Integer numPage = (Integer) request.getAttribute("page");
	Integer total = (Integer) request.getAttribute("total");
	String extraParam = request.getParameter("filter") != null ? String
			.format("&filter=%s", request.getParameter("filter")) : "";
	boolean isLastPage = (numPage + 1) * nbPerPage > total;
%>
<body>
	<header class="topbar">
		<h1 class="fill">
			<a href="<c:url value="ComputerServlet"/>">Computer database
				&mdash;</a>
		</h1>
	</header>

	<section id="main">
		<h1><%=total%>
			computers found
		</h1>

		<div id="actions">
			<form action="" method="GET">
				<input type="search" id="searchbox" name="filter" value="${param.filter}"
					placeholder="Filter by computer name..."> <input
					type="submit" id="searchsubmit" value="Filter by name"
					class="btn primary">
			</form>

			<a class="btn success" id="add"
				href="<c:url value="ComputerAddServlet"/>">Add a new computer</a>
		</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<th class="col2 header headerSortUp"><a href="http://localhost:9000/computers?s=-2">Computer
							name</a></th>
					<th class="col3 header "><a href="http://localhost:9000/computers?s=3">Introduced</a>
					</th>
					<th class="col4 header "><a href="http://localhost:9000/computers?s=4">Discontinued</a>
					</th>
					<th class="col5 header "><a href="http://localhost:9000/computers?s=5">Company</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${computers}">
					<tr>
						<td><a href="<c:url value="ComputerEditServlet?id=${c.id}"/>">${c.name}</a></td>
						<td><em><c:choose>
									<c:when test="${empty c.introduced}">-</c:when>
									<c:otherwise>
										<fmt:formatDate type="date" dateStyle="medium"
											value="${c.introduced}" />
									</c:otherwise>
								</c:choose></em></td>
						<td><em><c:choose>
									<c:when test="${empty c.discontinued}">-</c:when>
									<c:otherwise>
										<fmt:formatDate type="date" dateStyle="medium"
											value="${c.discontinued}" />
									</c:otherwise>
								</c:choose></em></td>
						<td><em><c:choose>
									<c:when test="${empty c.company}">-</c:when>
									<c:otherwise>${c.company.name}</c:otherwise>
								</c:choose></em></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="pagination" class="pagination">
			<ul>
				<li class="prev<%=numPage <= 0 ? " disabled" : ""%>"><a
					<%=(numPage <= 0) ? "" : " href=\"ComputerServlet?page="
					+ (numPage - 1) + extraParam + "\""%>>&larr;
						Previous</a></li>
				<li class="current"><a>Displaying <%=(numPage * nbPerPage + 1)%>
						to <%=Math.min(total.intValue(), ((numPage + 1) * nbPerPage))%>
						of <%=total%></a></li>
				<li class="next<%=isLastPage ? " disabled" : ""%>"><a
					<%=(isLastPage) ? "" : " href=\"ComputerServlet?page="
					+ (numPage + 1) + extraParam + "\""%>>Next
						&rarr;</a></li>
			</ul>
		</div>
	</section>
</body>
</html>
