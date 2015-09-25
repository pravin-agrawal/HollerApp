<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>login</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css" />

</head>
<body>

<script>

</script>

    <div class="login-card">
	    <h1>Log-in</h1><br>
	
		<f:form method="post" action="welcome.htm" commandName="user">
			Name:
			<f:input path="name" autocomplete="off" type="text" placeholder="Username"/><br>
			Password:
			<f:input path="password" type="password" placeholder="Password"/><br>
			<input type="submit" class="login login-submit" value="Login" />
		</f:form>
	</div>
</body>
</html>