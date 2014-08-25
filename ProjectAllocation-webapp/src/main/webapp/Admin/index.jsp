<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/jquery-1.7.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title id="demo">Insert title here</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="css/flat-ui.css">
<link href="images/favicon.ico" rel="shortcut icon">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="navbar navbar-inverse">
				<div class="navbar-inner">
					<div class="container">
						<button data-target="#nav-collapse-01" data-toggle="collapse"
							class="btn btn-navbar" type="button"></button>
						<div id="nav-collapse-01" class="nav-collapse collapse">
							<ul class="nav">
								<li class="active"><a href="Admin" id="adminname"> Admin</a></li>
								<li class="disabled"><a> Professor</a>
									<ul>
										<li><a href="Admin/addprof.html">Add/update a professor</a></li>
										<li><a href="Admin/delprof.html">Delete a professor</a></li>
									</ul>
								<li class="disabled"><a href="#fakelink"> Student</a>
									<ul>
										<li><a href="Admin/addstu.html">Add/update a student</a></li>
										<li><a href="Admin/delstu.html">Delete a student</a></li>
									</ul>
								<li>
								<li><a id="matching-btn"
									href="Admin/matching.html"> Matching </a></li>
							</ul>
						</div>
						<!--/.nav -->
					</div>
				</div>
			</div>
		</div>
		<div class="row" id="content">
			<p>test test test test test test test test test test test test
				test test test test test test test test test test test test test
				test test test test test test test test test test test test</p>
		</div>
		<div class="row">
			<a class="btn btn-large btn-block btn-danger" id="logout-btn"
				href="#fakelink">Log Out</a>
		</div>
	</div>
</body>
<script>
	$.ajax({ 
		type: "POST", 
		url: "restServices/services/AdminService/information/"+$.cookie('aid'),
		headers: {session: $.cookie('session')} 
	}).done(function( msg ) { 
		if (msg.status === 'fail' ) { 
			alert(msg.data.msg);
			window.location.href='Admin/login'; 
		} else if ( msg.status === 'success') {
			$("#adminname").html(msg.data.name);
		} 
	});
	$(function() {
        $("#logout-btn").on("click", function(){
           	$.ajax({
              type: "POST",
              url: "restServices/services/AdminService/logout",
              headers: {aid: $.cookie('aid'), session: $.cookie('session')},
              beforeSend : function (){
              } 
            }).done(function( msg ) {
            	if ( msg.status === 'fail' ) {
            		alert(msg.data.msg);
            	} else if ( msg.status === 'success' ) {
            		window.location.href='Admin/login';
            		$.cookie('aid', null);
					$.cookie('session', null);
            	}
            });
        })
      });
</script>
</html>