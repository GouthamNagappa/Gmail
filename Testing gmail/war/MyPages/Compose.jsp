<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel = "stylesheet">
<link href = "${pageContext.servletContext.contextPath}/MyPages/stylesheet.css" rel = "stylesheet">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.js"></script>
<title>Compose Mail</title>
</head>
<body style="background-color:#454444">
									<!-- Header part-->
									
	<div class = "navbar navbar-inverse navbar-static-top">
		<div class="container">
			<a href="/compose" class="navbar-brand"><span style="font-size: 35px; font-family: Casendra; font-weight:bold"><img src="/MyImages/createmail.jpg" height="35" width="35" style="padding-right:8px; padding-bottom:10px">Compose Mail</span></a>
		</div>
		<div class="container">
			<button style="padding-left:5px; padding-bottom:5px" class = " navbar-toggle" data-toggle="collapse" data-target = ".navHeaderCollapse">
				<span class = "icon-bar"></span>
				<span class = "icon-bar"></span>
				<span class = "icon-bar"></span>
			</button>
			<div class = "collapse navbar-collapse navHeaderCollapse">
				<ul class = "nav navbar-nav">
				
					<li><a href="/inbox">Inbox</a></li>
    				<li><a href = "/createtab">Create Tab</a></li>
				</ul>
			</div>
		</div>
	</div>
	
									<!-- body part -->
	
	<div  class="row"  >
		<div class="col-md-12"  >
			<div id="mail-panel" style="background-color:#f1f2f2; height:540px">
				<form action="/mailsent" method="post">
					${errorMessage}
					<table id="form_table" style="margin-left:20px;">
						
						<tr><td>Tab: </td><td><input type="text" class="form-control" name="belongsToTab"></td></tr>
						<tr><td>From: </td><td><input type="text" class="form-control" name="mailFromName"></td></tr>
						<tr><td>To: </td><td><input type="text" class="form-control" name="mailToName" value="goutham@xyz.com" readonly></td></tr>
						<tr><td>Subject: </td><td> <input type="text" class="form-control" name="mailSubject"></td></tr>
						<tr><td>Body: </td><td><textarea name = "mailBody" class="form-control"cols="50" rows="5"></textarea></td></tr>
						<tr><td></td><td><center> <input type="submit" value="Send" style="width:100px">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="button" value="Discard" onclick="window.location='/inbox'" style="width:100px"></center></td></tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	
</body>
</html>