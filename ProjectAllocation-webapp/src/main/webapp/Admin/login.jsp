<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">

<script type="text/javascript" src="../js/jquery-1.7.js"></script>
<script type="text/javascript" src="../js/jquery.cookie.js"></script>
<title>Admin Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Loading Bootstrap -->
<link href="../bootstrap/css/bootstrap.css" rel="stylesheet">

<!-- Loading Flat UI -->
<link href="../css/style.css" rel="stylesheet">

<link rel="shortcut icon" href="../images/favicon.ico">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->
</head>
<body>


	<section class="container">
		<div class="span5">
			<div class="login-form">
				<div class="log-title">
					<img src="../images/login/login-icon.png" alt="login">
					<p>Admin login</p>
				</div>
				<div class="control-group">
					<input type="text" class="login-field" value=""
						placeholder="Admin id" id="login-name" /> <label
						class="login-field-icon fui-user" for="login-name"></label>
				</div>

				<div class="control-group">
					<input type="password" class="login-field" value=""
						placeholder="password" id="login-pass" /> <label
						class="login-field-icon fui-lock" for="login-pass"></label>
				</div>

				<a class="btn btn-large btn-block disabled" id='login-btn'>Login</a>
				<a href="/" class="login-link">Back to main page</a>
			</div>
		</div>
	</section>
</body>
<script>
      $(function() {
        $("#login-btn").on("click", function(){
           	$.ajax({
              type: "POST",
              url: "../restServices/services/AdminService/login",
              headers: {aid: $("#login-name").val(), password: $("#login-pass").val()?$("#login-pass").val():""},
              beforeSend : function (){
              	$("#login-btn").css("btn btn-large btn-block disabled");
              } 
            }).done(function( msg ) {
            	if ( msg.status === 'fail' ) {
            		alert(msg.data.msg);
            	} else if ( msg.status === 'success' ) {
            		window.location.href='/Admin';
            		$.cookie('aid', msg.data.aid);
					$.cookie('session', msg.session);
            	}
            });
        })
      });
  </script>
</html>
