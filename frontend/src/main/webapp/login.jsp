<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<form method="post" id="loginForm" action="<c:url value='j_spring_security_check'/>">
		<input type="text" name="j_username" placeholder="Username" id="j_username"/>
		<input type="password" name="j_password" placeholder="Password" id="j_password"/>
		<input type="submit"/>
	</form>

</body>
</html>