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
		
		function btnUpdatePostClick(postId){
			var elementId = "existingPostContent+" + postId
			//alert(elementId)
			$("#existingPostContent+" + postId).removeAttr('readonly');
		}
		
		
	</script>
	
	<div class="container">
			
		<div class ="row">
			<div class="col-md-3">
				<div class="profile-sidebar">
					<!-- SIDEBAR USERPIC -->
					
					<div class="profile-userpic">
						<img src="data:image/png;base64,${PROFILEPICTURE} " class="img-responsive" alt="Profile Picture">
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
				   <form method="post" action="${pageContext.request.contextPath}/createpost">
						
						<ul class="nav nav-tabs">
						  <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/personalposts">My Posts</a></li>
						  <li role="presentation"><a href="${pageContext.request.contextPath}/newsfeed">News Feed</a></li>
						</ul>
						<br><br>
						<label for="postInterest">Select Your Interests</label> 
						<select class="form-control" name="postInterest">
						  <c:forEach var="entry" items="${INTEREST_LIST}">
						  	<option value= ${entry.getInterestId()}> ${entry.getInterestName()} </option>
						  </c:forEach>
						</select>
						<br>
						<label for="postContent">Enter Post Text</label>
						<textarea class="form-control" name="postContent"></textarea>
						<br>
						<button class="btn btn-primary btn-md pull-right" id="btnCreatePost" type="submit"/>Post</button>
					</form>
					<br><br>
					<hr>
					
					<h4>Your Stories</h4>
					
					<c:forEach var="post" items="${PERSONALPOSTS}">
						<form method="post" action="${pageContext.request.contextPath}/updateordeletepost">
							<div class="panel panel-default">
							  <div class="panel-heading">
							  	<div class="row">
							  		<div class="col-md-10">
							    		<h3 class="panel-title">${post.getProfile().getUser().getUserName() }</h3>
							    	</div>
							    	
							    	<div class="col-md-1">
								    	<button id="btnUpdatePost" name="btnUpdatePost" type="button" class="btn btn-default btn-sm" onClick="btnUpdatePostClick(${post.getPostId()})">
											  <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										</button>
									</div>
									<div class="col-md-1">
								    	<button name="btnDeletePost" type="submit" class="btn btn-default btn-sm">
											  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
										</button>
									</div>
							    </div>
							  </div>
							  
							  <div class="panel-body">
								    <input name="postId" type="hidden" value="${post.getPostId()}"/>
								    <div class="row">
								    	<div class="col-md-9">
									    	<strong>Tag:</strong> <c:out value="${post.getInterest().getInterestName() }" />
									    </div>
									    <div class="col-md-3">
								    		<c:out value="${post.getLastUpdatedAt()}" />
								    	</div>
							    	</div>
								    <br/>
								    <textarea id="existingPostContent+${post.getPostId()}" class="form-control input-block-level" name="existingPostContent" readonly> ${post.getPostContent()} </textarea>
								    <hr/>
								    <div class="row">
								    		&nbsp &nbsp
									    	<span class="glyphicon glyphicon-thumbs-up p-x-3" aria-hidden="true"></span> ${post.getUpCount()}
											&nbsp &nbsp 
										
											<span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span> ${post.getDownCount()}
											&nbsp &nbsp
										
											<span class="glyphicon glyphicon-heart" aria-hidden="true"></span> ${post.getHappyCount()}
											&nbsp &nbsp
										
											<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span> ${post.getSadCount()}
										
								    </div>
							  </div>
							</div>
						</form>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>	<!-- Close of main container -->
</body>
</html>