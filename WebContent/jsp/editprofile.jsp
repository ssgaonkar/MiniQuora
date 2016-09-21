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
	<form class="form-group" method="post" action="${pageContext.request.contextPath}/updateuserprofile" enctype="multipart/form-data">
		<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/autosize.min.js" />"></script>
	
		<script type="text/javascript">
			$(document).ready( function() {
				autosize($('textarea'));
			});
		</script>
	
		<label for="bioInfo">Enter Biological Information</label>
		<textarea class="form-control" name = "bioInfo">${USEROBJ.getProfile().getBioInfo()}</textarea>
		
		<label for="file">Upload Profile picture</label>
		<input class="form-control-file" type = "file" name = "file"/>
		
		<label for="profileInterests">Enter your Interests</label> 
		<select class="form-control" name="profileInterests" multiple>
		  <c:forEach var="allInterests" items="${INTEREST_LIST}">
		  	<c:set var="selectFlag" scope="page" value="false"/>
		  	<c:forEach var="profileInterest" items="${PROFILE_INTEREST_LIST}">
		  		<c:if test="${allInterests.getInterestId() == profileInterest.getInterestId()}">
   					<c:set var="selectFlag" scope="page" value="true"/>
				</c:if>		  		
		  	</c:forEach>
		  	
		  	<c:choose>
			   <c:when test="${selectFlag eq true}">
			   		allInterests.getInterestId()
			   		<option selected value= ${ allInterests.getInterestId()}> ${allInterests.getInterestName()} </option>
			   </c:when>
			   <c:otherwise>
			   		<option value= ${allInterests.getInterestId()}> ${allInterests.getInterestName()} </option> 
			   </c:otherwise>
			</c:choose>
	  	  </c:forEach>
		</select>
		<br>
		<button class="btn btn-primary" id="btnUpdateProfile" type="submit"/>Update Profile</button>
		<form class="form-group" method="get" action="${pageContext.request.contextPath}/redirecttopersonalposts" enctype="multipart/form-data">
			<button class="btn btn-primary" id="btnCancel" type="submit"/>Cancel</button>
		</form>
	</form>
	</div>
		</div>
	</div>
</body>
</html>