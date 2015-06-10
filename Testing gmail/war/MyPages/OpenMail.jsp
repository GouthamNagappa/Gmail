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
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Mail</title>
</head>
<body id="htmlBody" style="background-color:#454444">
									<!-- Header part-->
									
	<div class = "navbar navbar-inverse navbar-static-top">
		<div class="container">
			<a href="#" class="navbar-brand"><span style="font-size: 35px; font-family: Casendra; font-weight:bold"><img src="/MyImages/mailIcon.jpg" height="35" width="35" style="padding-right:8px; padding-bottom:10px"/>Mail</span></a>
		</div>
		<div class="container">
			<button style="padding-left:5px; padding-bottom:5px" class = " navbar-toggle" data-toggle="collapse" data-target = ".navHeaderCollapse">
				<span class = "icon-bar"></span>
				<span class = "icon-bar"></span>
				<span class = "icon-bar"></span>
			</button>
			<div class = "collapse navbar-collapse navHeaderCollapse">
				<ul class = "nav navbar-nav">
					<li><a href = "/inbox">Inbox</a></li>
					<li><a href="/compose">Compose Mail</a></li>
    				<li><a href ="/createtab">Create Tab</a><li>
				</ul>
			</div>
		</div>
	</div>
	
									<!-- Body -->
	<div  class="row">
		<div class="col-sm-12">
			<div id="mail_panel" style="background-color:#f1f2f2; height:540px"><br/>
				<div id= "icon_tab" class="container" style="border-radius:10px; border:2px solid #b8b8b8">
					<script type="text/javascript">
					//Go Back
						var goBack = document.createElement("A");
						goBack.setAttribute("class","btn btn-default btn-xs pull-left ");
						goBack.setAttribute("href","/inbox");
						var goBackText = document.createTextNode("Go Back");
						goBack.appendChild(goBackText);
						document.getElementById("icon_tab").appendChild(goBack);
						
					
					
					 //Move left
						var moveLeft = document.createElement("A");
					 	if((${mailIndex}-1)>=0){
					 		moveLeft.setAttribute("href","/openmail/"+${tabIndex}+"/"+(${mailIndex}-1));
					 	}else{
					 		moveLeft.setAttribute("href","#");
					 	}
						var imageLeft = document.createElement("SPAN");
						imageLeft.setAttribute("class","glyphicon glyphicon-chevron-left");
						imageLeft.setAttribute("style","margin-left:390px; margin-right:20px");
						imageLeft.setAttribute("title","Previous Mail");
						moveLeft.appendChild(imageLeft);
					
						document.getElementById("icon_tab").appendChild(moveLeft); 
						
					//star mail
						
						var starAnchor = document.createElement("A");
						starAnchor.setAttribute("href","#")
						starAnchor.setAttribute("onclick","starChange()");
				
						var starImage = document.createElement("IMG");
						if(${starFlag}==false){
							starImage.setAttribute("src","${pageContext.servletContext.contextPath}/MyImages/unselectstar2.jpg");
						}else{
							starImage.setAttribute("src","${pageContext.servletContext.contextPath}/MyImages/selectedstar2.jpg");
						}
						starImage.setAttribute("width","20px");
						starImage.setAttribute("height","20px");
						starImage.setAttribute("style","margin-left:20px; margin-right:20px");
						starImage.setAttribute("title","Star/Unstar Mail");
				
					
						function starChange(){
							$.ajax({
								url:'/startoggle',
								data:'tabIndex=${tabIndex}&mailIndex=${mailIndex}', 
								
								type:'GET',
								success:function(starStatus){
									if(starStatus=="false"){
										starImage.setAttribute("src","${pageContext.servletContext.contextPath}/MyImages/unselectstar2.jpg");
									}else{
										starImage.setAttribute("src","${pageContext.servletContext.contextPath}/MyImages/selectedstar2.jpg");
									}

								}
							});
						}
						

						starAnchor.appendChild(starImage);
						document.getElementById("icon_tab").appendChild(starAnchor);

					
					//delete mail
						var deleteIcon = document.createElement("A");
						deleteIcon.setAttribute("href","/deletemail?tabIndex="+${tabIndex}+"&mailIndex="+${mailIndex});
						
						var deleteImage = document.createElement("IMG");
						deleteImage.setAttribute("src","${pageContext.servletContext.contextPath}/MyImages/delecteicon.jpg");
						deleteImage.setAttribute("width","20px");
						deleteImage.setAttribute("height","20px");
						deleteImage.setAttribute("style","margin-left:20px; margin-right:20px");
						deleteImage.setAttribute("title","Delete Mail");
						
						deleteIcon.appendChild(deleteImage);
						document.getElementById("icon_tab").appendChild(deleteIcon);
						
					//mark unread
						var unreadIcon = document.createElement("A");
						unreadIcon.setAttribute("href","/markunread?tabIndex="+${tabIndex}+"&mailIndex="+${mailIndex});
						
						var uneadImage = document.createElement("IMG");
						uneadImage.setAttribute("src","${pageContext.servletContext.contextPath}/MyImages/unread.jpg");
						uneadImage.setAttribute("width","25px");
						uneadImage.setAttribute("height","25px");
						uneadImage.setAttribute("style","margin-left:20px; margin-right:20px");
						uneadImage.setAttribute("title","Mark as Unread");
						
						unreadIcon.appendChild(uneadImage);
						document.getElementById("icon_tab").appendChild(unreadIcon);
						
					//move right
						var moveRight = document.createElement("A");
						if((${mailIndex}+1)<${maxNumberOfMails}){
							moveRight.setAttribute("href","/openmail/"+${tabIndex}+"/"+(${mailIndex}+1));
					 	}else{
					 		moveRight.setAttribute("href","#");
					 	}
						var imageRight = document.createElement("SPAN");
						imageRight.setAttribute("class","glyphicon glyphicon-chevron-right");
						imageRight.setAttribute("title","Next Mail");
						imageRight.setAttribute("style","margin-left:20px; margin-right:20px");
						moveRight.appendChild(imageRight);
					
						document.getElementById("icon_tab").appendChild(moveRight); 
					
					
					</script>
					<div class="btn-group pull-right" role="group">
   						 <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
   						 		Move To <span class="caret"></span></button>
   				 		 <ul id="dropList" class="dropdown-menu" role="menu">
      						<script type="text/javascript">
      							var tabNamesList = ${tabNamesList};
      							for(var i=0;i<tabNamesList.length;i++){
      								var list = document.createElement("LI");
      								var anchor = document.createElement("A");
      								anchor.setAttribute("href","/movemail?tabIndex="+${tabIndex}+"&mailIndex="+${mailIndex}+"&moveToTabIndex="+i);
      								
      								var text = document.createTextNode(tabNamesList[i]);
      								anchor.appendChild(text);
      								list.appendChild(anchor);
      								document.getElementById("dropList").appendChild(list);
      							}
      							
      						</script>
    					</ul>
  					</div>
				</div>
				<br/>
				<div class="container" style="background-color:#b8b8b8; border-radius:10px">
					<br/>
					<p>From:<span class="badge pull-right">${mailTime}</span></p><div class="well well-sm">${mailFromName}</div>
					<div class="panel panel-default">
						<div class="panel-heading">Subject: ${mailSubject}</div>
						<div class="panel-body" style="overflow-x:auto;">${mailBody }</div>
					</div>
					<div id = "anchorDiv">
					<p id="paraDiv" style="font-color:black">Click here to <a href="#openMailDiv" onclick="reply()">Reply</a> or <a href="#openMailDiv" onclick="forward()">Forward</a></p>
					</div>
					<div id = "openMailDiv" >
					<script>
						function reply(){
							var anchorDiv = document.getElementById("anchorDiv");
							anchorDiv.removeChild(document.getElementById("paraDiv"));
							
							
							var form = document.createElement("FORM");
							
							var replyTo = document.createElement("INPUT");
							replyTo.setAttribute("class","form-control");
							replyTo.setAttribute("name","replyTo");
							replyTo.setAttribute("value","Reply To: ${mailFromName}");
							
							var replyMessage = document.createElement("TEXTAREA");
							replyMessage.setAttribute("class","form-control");
							replyMessage.setAttribute("name","replyMessage");
							replyMessage.setAttribute("rows","5");
							replyMessage.setAttribute("placeHolder","Enter Message");
							replyMessage.setAttribute("style","margin-top:10px");
							replyMessage.setAttribute("style","margin-bottom:10px");
							
							var replyButton = document.createElement("INPUT");
							replyButton.setAttribute("type","button");
							replyButton.setAttribute("value","Reply");
							replyButton.setAttribute("style","width:100px; margin: 10px 30px 10px 450px");
							
							var replyDiscardButton = document.createElement("INPUT");
							replyDiscardButton.setAttribute("type","button");
							replyDiscardButton.setAttribute("value","Discard");
							replyDiscardButton.setAttribute("style","width:100px; margin: 10px 30px 10px 30px");
							replyDiscardButton.setAttribute("onclick","window.location='/openmail/${tabIndex}/${mailIndex}'");
							
							
							form.appendChild(replyTo);
							form.appendChild(replyMessage);
							form.appendChild(replyButton);
							form.appendChild(replyDiscardButton);
							document.getElementById("openMailDiv").appendChild(form);
						}
						
						function forward(){
							var anchorDiv = document.getElementById("anchorDiv");
							anchorDiv.removeChild(document.getElementById("paraDiv"));
							
							var form = document.createElement("FORM");
							
							var forwardTo = document.createElement("INPUT");
							forwardTo.setAttribute("class","form-control");
							forwardTo.setAttribute("name","replyTo");
							forwardTo.setAttribute("placeHolder","Forward To:");
							
							var forwardMessage = document.createElement("TEXTAREA");
							forwardMessage.setAttribute("class","form-control");
							forwardMessage.setAttribute("name","replyMessage");
							forwardMessage.setAttribute("rows","5");
							forwardMessage.setAttribute("style","margin-top:10px");
							
							var mailBody = document.createTextNode("${mailBody}");
							forwardMessage.appendChild(mailBody);
							
							var forwardButton = document.createElement("INPUT");
							forwardButton.setAttribute("type","button");
							forwardButton.setAttribute("value","Forward");
							forwardButton.setAttribute("style","width:100px; margin: 10px 30px 10px 450px");
							
							var forwardDiscardButton = document.createElement("INPUT");
							forwardDiscardButton.setAttribute("type","button");
							forwardDiscardButton.setAttribute("value","Discard");
							forwardDiscardButton.setAttribute("style","width:100px; margin: 10px 30px 10px 30px");
							forwardDiscardButton.setAttribute("onclick","window.location='/openmail/${tabIndex}/${mailIndex}'");
							
							
							form.appendChild(forwardTo);
							form.appendChild(forwardMessage);
							form.appendChild(forwardButton);
							form.appendChild(forwardDiscardButton);
							document.getElementById("openMailDiv").appendChild(form);
						}
					</script>
					</div>
				</div>
 			</div>
		</div>
	</div>
	
</body>
</html>