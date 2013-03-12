<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" --%>
<%--     pageEncoding="UTF-8"%> --%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="com.excilys.model.Computer"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Computers database</title>
<link rel="stylesheet" media="screen"
	href="<c:url value="/css/bootstrap.min.css"/>">
<link rel="stylesheet" media="screen"
	href="<c:url value="/css/main.css"/>">
</head>
<c:if test="${!empty param.filter}">
	<c:set var="extraParamFilter" value="&filter=${param.filter}" />
</c:if>
<c:if test="${!empty param.filter}">
	<c:set var="extraParam" value="&filter=${param.filter}" />
</c:if>
<c:if test="${!empty param.sort}">
	<c:set var="extraParam" value="&sort=${param.sort}" />
</c:if>
<body>
	<header class="topbar">
		<h1 class="fill">
			<a href="<c:url value="computers.do"/>">Computer database
				&mdash;</a>
		</h1>
	</header>

	<section id="main">
		<h1>${total} computers found</h1>
    
    	<c:if test="${!empty action}">
        <div class="alert-message warning">
            <strong>Done!</strong> Computer ${computer} has been ${action}
        </div>
        </c:if>

		<div id="actions">
			<form action="<c:url value="computers.do"/>" method="GET">
				<input type="search" id="searchbox" name="filter"
					value="${param.filter}" placeholder="Filter by computer name...">
				<input type="submit" id="searchsubmit" value="Filter by name"
					class="btn primary">
			</form>

			<a class="btn success" id="add"
				href="<c:url value="/computers/new.do"/>">Add a new computer</a>
		</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<th
						class="col2 header<c:if test="${sorter.isCurrent(1)}"> headerSort<c:choose><c:when test="${sorter.isReverse(1)}">Down</c:when><c:otherwise>Up</c:otherwise></c:choose></c:if>"><a
						href="<c:url value="computers.do?sort=${sorter.getSort(1)}${extraParamFilter}"/>">Computer
							name</a></th>
					<th
						class="col3 header<c:if test="${sorter.isCurrent(2)}"> headerSort<c:choose><c:when test="${sorter.isReverse(2)}">Down</c:when><c:otherwise>Up</c:otherwise></c:choose></c:if>"><a
						href="<c:url value="computers.do?sort=${sorter.getSort(2)}${extraParamFilter}"/>">Introduced</a></th>
					<th
						class="col4 header<c:if test="${sorter.isCurrent(3)}"> headerSort<c:choose><c:when test="${sorter.isReverse(3)}">Down</c:when><c:otherwise>Up</c:otherwise></c:choose></c:if>"><a
						href="<c:url value="computers.do?sort=${sorter.getSort(3)}${extraParamFilter}"/>">Discontinued</a></th>
					<th
						class="col5 header<c:if test="${sorter.isCurrent(4)}"> headerSort<c:choose><c:when test="${sorter.isReverse(4)}">Down</c:when><c:otherwise>Up</c:otherwise></c:choose></c:if>"><a
						href="<c:url value="computers.do?sort=${sorter.getSort(4)}${extraParamFilter}"/>">Company</a></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${computers}">
					<tr>
						<td><a href="<c:url value="/computers/${c.id}.do"/>">${c.name}</a></td>
						<td><c:choose>
									<c:when test="${empty c.introduced}"><em>-</em></c:when>
									<c:otherwise>
										<joda:format value="${c.introduced}" style="M-" />
									</c:otherwise>
								</c:choose></td>
						<td><c:choose>
									<c:when test="${empty c.discontinued}"><em>-</em></c:when>
									<c:otherwise>
										<joda:format value="${c.discontinued}" style="M-" />
									</c:otherwise>
								</c:choose></td>
						<td><c:choose>
									<c:when test="${empty c.company}"><em>-</em></c:when>
									<c:otherwise>${c.company.name}</c:otherwise>
								</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="pagination" class="pagination">
			<ul>
				<li class="prev<c:out value="${page <= 0 ? ' disabled' : '' }"/>"><a
					<c:if test="${page > 0}"> href="computers.do?page=${page - 1}${extraParam}"</c:if>>&larr;
						Previous</a></li>
				<li class="current"><a>Displaying ${page * nbPerPage + 1}
						to ${numMax} of ${total} </a></li>
				<li class="next<c:out value="${(page + 1) * nbPerPage > total ? ' disabled' : ''}"/>"><a
					<c:if test="${(page + 1) * nbPerPage < total}"> href="computers.do?page=${page + 1}${extraParam}"</c:if>>Next
						&rarr;</a></li>
			</ul>
		</div>
	</section>
</body>
</html>
