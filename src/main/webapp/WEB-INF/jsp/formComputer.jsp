<%@page import="com.excilys.model.Company"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="com.excilys.model.Computer"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<body>
	<header class="topbar">
		<h1 class="fill">
			<a href="<c:url value="/computers.do"/>">Computer database
				&mdash;</a>
		</h1>
	</header>

	<section id="main">
		<h1><c:choose><c:when test="${isEdit}"></c:when><c:otherwise>Add a computer</c:otherwise></c:choose></h1>
		<form action="<c:url value="${action}"/>" method="POST">
			<fieldset>
				<div
					class="clearfix<c:if test="${!empty validator && !validator.nameValid}"> error</c:if>">
					<label for="name">Computer name</label>
					<div class="input">
						<input type="text" id="name" name="name" value="${computer.name}${param.name}">
						<span class="help-inline">Required</span>
					</div>
				</div>

				<div
					class="clearfix<c:if test="${!empty validator && !validator.introducedValid}"> error</c:if>">
					<label for="introduced">Introduced date</label>
					<div class="input">
						<input type="text" id="introduced" name="introduced"
							value="${computer.introduced}${param.introduced}"> <span class="help-inline">Date
							('yyyy-MM-dd')</span>
					</div>
				</div>

				<div
					class="clearfix<c:if test="${!empty validator && !validator.discontinuedValid}"> error</c:if>">
					<label for="discontinued">Discontinued date</label>
					<div class="input">
						<input type="text" id="discontinued" name="discontinued"
							value="${computer.discontinued}${param.discontinued}"> <span class="help-inline">Date
							('yyyy-MM-dd')</span>
					</div>
				</div>

				<div class="clearfix ">
					<label for="company">Company</label>
					<div class="input">
						<select id="company" name="company">
							<option class="blank" value="">-- Choose a company --</option>
							<c:forEach var="c" items="${companies}">
								<option value="${c.id}" <c:if 
									test="${c.id == param.company || c.id == computer.company.id}"> 
									selected="selected"</c:if>>${c.name}</option>
							</c:forEach>
						</select> <span class="help-inline"></span>
					</div>
				</div>
			</fieldset>
			<div class="actions">
				<input type="submit" value="<c:choose><c:when test="${isEdit}">Save this computer</c:when><c:otherwise>Create this computer</c:otherwise></c:choose>"
					class="btn primary"> or <a
					href="<c:url value="/computers.do"/>" class="btn">Cancel</a>
			</div>
		</form>

		<c:if test="${isEdit}">
		<form action="<c:url value="/computers/${id}/delete.do"/>" method="POST" class="topRight">
			<input type="submit" value="Delete this computer" class="btn danger">
		</form>
		</c:if>
	</section>
</body>
</html>