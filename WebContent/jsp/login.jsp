<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Mini Quora</title>
	<link rel="stylesheet" type="text/css" href ="<c:url value="/resources/css/bootstrap.min.css" />"  />
</head>
<body>
	<div class="container">
	
		<div class ="row">
		
			<div class="col-md-4 col-md-offset-4">
				<form method="post" action="${pageContext.request.contextPath}/login">
					<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
					<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
					<label for="userName">Username</label>
					<input class="form-control" type="text" name="userName"/>
					<label for="password">Password</label>
					<input class="form-control" type="password" name="password"/>
					<br>
					<button class="btn btn-primary col-md-4 col-md-offset-3" id="btnLogin" type="submit"/>Login</button>
				</form>
				<form method="get" action="${pageContext.request.contextPath}/register">
					<button class="btn btn-primary col-md-3 col-md-offset-1" id="btnRegister" type="submit"/>Register</button>
				</form>
				
				<!--<div class ="row">
					<form method="post" action="${pageContext.request.contextPath}/login">
						<button class="btn btn-primary col-md-4 col-md-offset-3" id="btnLogin" type="submit"/>Login</button>
					</form>
					<form method="get" action="${pageContext.request.contextPath}/register">
						<button class="btn btn-primary col-md-3 col-md-offset-1" id="btnRegister" type="submit"/>Register</button>
					</form>
				</div>-->
			</div>
		</div>
	</div>
</body>
</html>