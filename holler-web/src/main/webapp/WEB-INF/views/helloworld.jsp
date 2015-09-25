<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring 4 MVC -HelloWorld</title>
</head>
<body>
<script>

function logout(){
	document.getElementById("logoutFormId").submit();
}
</script>
	<center>
		<h2>Hello World</h2>
		<h2>
			${message} ${name}
		</h2>
		<br/>
		<f:form method="post" action="logout.htm" id="logoutFormId">
			<a href="#" id="logoutId" onclick="logout()">Logout</a>
		</f:form>
	</center>
</body>
</html>