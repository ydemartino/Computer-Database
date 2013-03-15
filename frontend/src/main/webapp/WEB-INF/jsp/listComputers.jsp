<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="com.excilys.model.Computer"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
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
<c:if test="${!empty param.company}">
	<c:set var="extraParamFilter" value="${extraParamFilter}&company=${param.company}" />
</c:if>
<c:if test="${!empty extraParamFilter}">
	<c:set var="extraParam" value="${extraParamFilter}" />
</c:if>
<c:if test="${!empty param.sort}">
	<c:set var="extraParam" value="${extraParam}&sort=${param.sort}" />
</c:if>
<body>
	<header class="topbar">
		<h1 class="fill">
			<a href="<c:url value="computers.do"/>">Computer database &mdash;</a>
		</h1>
	</header>

	<section id="main">
		<h1>${pager.totalElements} computers found</h1>

		<c:if test="${!empty action}">
			<div class="alert-message warning">
				<strong>Done!</strong> Computer ${computer} has been ${action}
			</div>
		</c:if>

		<div id="actions">
			<form action="<c:url value="computers.do"/>" method="GET">
				<input type="search" id="searchbox" name="filter"
					value="${param.filter}" placeholder="Filter by computer name...">
				<input type="search" name="company"
					value="${param.company}" placeholder="Filter by company name...">
				<input type="submit" id="searchsubmit" value="Filter"
					class="btn primary">
			</form>

			<a class="btn success" id="add"
				href="<c:url value="/computers/new.do"/>">Add a new computer</a>
		</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<tag:thSorted thLabel="Computer name" colId="1"
						extraParamFilter="${extraParamFilter}" sorter="${sorter}" />
					<tag:thSorted thLabel="Introduced" colId="2"
						extraParamFilter="${extraParamFilter}" sorter="${sorter}" />
					<tag:thSorted thLabel="Discontinued" colId="3"
						extraParamFilter="${extraParamFilter}" sorter="${sorter}" />
					<tag:thSorted thLabel="Company" colId="4"
						extraParamFilter="${extraParamFilter}" sorter="${sorter}" />
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${pager.content}">
					<tr>
						<td><a href="<c:url value="/computers/${c.id}.do"/>">${c.name}</a></td>
						<td><c:choose>
								<c:when test="${empty c.introduced}">
									<em>-</em>
								</c:when>
								<c:otherwise>
									<joda:format value="${c.introduced}" style="M-" />
								</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${empty c.discontinued}">
									<em>-</em>
								</c:when>
								<c:otherwise>
									<joda:format value="${c.discontinued}" style="M-" />
								</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${empty c.company}">
									<em>-</em>
								</c:when>
								<c:otherwise>${c.company.name}</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="pagination" class="pagination">
			<tag:paginate pager="${pager}" extraParam="${extraParam}"/>
		</div>
	</section>
</body>
</html>
