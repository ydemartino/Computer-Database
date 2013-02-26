<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" --%>
<%--     pageEncoding="UTF-8"%> --%>
<%@page import="com.excilys.model.Company"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="com.excilys.model.Computer"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computers database</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value="/css/bootstrap.min.css"/>">
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value="/css/main.css"/>">
</head>

<body>
	<header class="topbar">
		<h1 class="fill">
			<a href="<c:url value="ComputerServlet"/>">Computer database
				&mdash;</a>
		</h1>
	</header>

	<section id="main">
		<h1>Add a computer</h1>
		<form action="<c:url value="ComputerAddServlet"/>" method="POST">
			<fieldset>
				<div class="clearfix<c:if test="${!empty validator && !validator.nameValid}"> error</c:if>">
					<label for="name">Computer name</label>
					<div class="input">
						<input type="text" id="name" name="name" value="${param.name}">
						<span class="help-inline">Required</span>
					</div>
				</div>

				<div class="clearfix<c:if test="${!empty validator && !validator.introducedValid}"> error</c:if>">
					<label for="introduced">Introduced date</label>
					<div class="input">
						<input type="text" id="introduced" name="introduced"
							value="${param.introduced}">
							<span class="help-inline">Date ('yyyy-MM-dd')</span>
					</div>
				</div>

				<div class="clearfix<c:if test="${!empty validator && !validator.discontinuedValid}"> error</c:if>">
					<label for="discontinued">Discontinued date</label>
					<div class="input">
						<input type="text" id="discontinued" name="discontinued"
							value="${param.discontinued}">
						<span class="help-inline">Date ('yyyy-MM-dd')</span>
					</div>
				</div>

				<div class="clearfix ">
					<label for="company">Company</label>
					<div class="input">
						<select id="company" name="company">
							<option class="blank" value="">-- Choose a company --</option>
							<c:forEach var="c" items="${companies}">
								<option value="${c.id}"<c:if test="${c.id == param.company}"
								> selected="selected"</c:if>>${c.name}</option>
							</c:forEach>
						</select> <span class="help-inline"></span>
					</div>
				</div>
			</fieldset>
			<div class="actions">
				<input type="submit" value="Create this computer"
					class="btn primary"> or <a
					href="<c:url value="ComputerServlet"/>" class="btn">Cancel</a>
			</div>
		</form>

	</section>
</body>
</html>