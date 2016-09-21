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
	<link rel="stylesheet" type="text/css" href ="<c:url value="/resources/css/profile.css" />"  />
</head>
<body>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.1.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/autosize.min.js" />"></script>
	
	<script type="text/javascript">
		$(document).ready( function() {
			autosize($('textarea'));
		});
	</script>
	
	<div class="container">
		<div class ="row"></div>	
		<div class ="row">
			<div class="col-md-3">
				<div class="profile-sidebar">
					<!-- SIDEBAR USERPIC -->
					<div class="profile-userpic">
						<img src="data:image/png;base64,${PROFILEPICTURE}" class="img-responsive" alt="Profile Picture">
					</div>
					<!-- END SIDEBAR USERPIC -->
					<!-- SIDEBAR USER TITLE -->
					<div class="profile-usertitle">
						<div class="profile-usertitle-name">
							${ USEROBJ.getUserName()}
						</div>
						
					</div>
					<!-- END SIDEBAR USER TITLE -->
					<!-- SIDEBAR BUTTONS -->
					<div class="profile-userbuttons">
						<div class="row">
							<form method="get" action="${pageContext.request.contextPath}/redirecttoeditprofile">
								<button type="submit" class="btn btn-success btn-sm col-md-4 col-md-offset-2">Edit Profile</button>
							</form>
							<form method="post" action="${pageContext.request.contextPath}/logout">
								<button type="submit" class="btn btn-danger btn-sm col-md-3 col-md-offset-1">Logout</button>
							</form>
						</div>
					</div>
					<!-- END SIDEBAR BUTTONS -->
				</div>
			</div>
			
			<div class="col-md-9">
				<div class="profile-content">
					<ul class="nav nav-tabs">
					  <li role="presentation"><a href="${pageContext.request.contextPath}/personalposts">My Posts</a></li>
					  <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/newsfeed">News Feed</a></li>
					</ul>
					<br><br>
					
					<h4>Top Stories For You</h4>
						
						<c:forEach var="post" items="${NEWSFEED}">
							<form method="post" action="${pageContext.request.contextPath}/incrementcount">
								<div class="panel panel-default">
								  <div class="panel-heading">
								    <div class="row">
								  		<div class="col-md-9">
								    		<h3 class="panel-title">${post.getProfile().getUser().getUserName() }</h3>
								    	</div>
								    	<div class="col-md-3">
								    		<c:out value="${post.getLastUpdatedAt()}" />
								    	</div>
								    	
								    </div>
								  </div>
								  <div class="panel-body">
								    	<input name="postId" type="hidden" value="${post.getPostId()}"/>
									    <strong>Tag:</strong> <c:out value="${post.getInterest().getInterestName() }" />
									    <br/>
									    <c:out value="${post.getPostContent() }" />
									    <hr/>
									    <div class="row">
									    	<div class="col-md-1">
										    	<button name="btnIncrementUpCount" type="submit" class="btn btn-default btn-sm">
												  <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span> ${post.getUpCount()}
												</button>
											</div>
											<div class="col-md-2">
												<button name="btnIncrementDownCount" type="submit" class="btn btn-default btn-sm">
												  <span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span> ${post.getDownCount()}
												</button>
											</div>
											<div class="col-md-2">
												<button name="btnIncrementHappyCount" type="submit" class="btn btn-default btn-sm">
												  <span class="glyphicon glyphicon-heart" aria-hidden="true"></span> ${post.getHappyCount()}
												</button>
											</div>
											<div class="col-md-3">
												<button name="btnIncrementSadCount" type="submit" class="btn btn-default btn-sm">
												  <span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span> ${post.getSadCount()}
												</button>
											</div>
								 	 	</div>
									</div>
								</div>
							</form>
						</c:forEach>
				
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>