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
	<br>
	<form class="form-group" method="post" action="${pageContext.request.contextPath}/createuserprofile" enctype="multipart/form-data">
		<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
		<label for="bioInfo">Enter Biological Information</label>
		<textarea class="form-control" name = "bioInfo"></textarea>
		
		<label for="file">Upload Profile picture</label>
		<input class="form-control-file" type = "file" name = "file"/>
		
		<label for="profileInterests">Enter your Interests</label> 
		<select class="form-control" name="profileInterests" multiple>
		  <c:forEach var="entry" items="${INTEREST_LIST}">
		  	<option value= ${entry.getInterestId()}> ${entry.getInterestName()} </option>
		  </c:forEach>
		</select>
		<br>
		<button class="btn btn-primary" id="btnCreateProfile" type="submit"/>Save Profile</button>
		
	</form>
	</div>
		</div>
	</div>
</body>
</html>