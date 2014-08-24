<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/jquery-1.7.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title id="demo">Insert title here</title>
<script>
	function verification(aid, session)
	{
	
	}
	
	$.ajax({
              type: "POST",
              url: "restServices/services/AdminService/information/"+$.cookie('aid'),
              headers: {session: $.cookie('session')}
            }).done(function( msg ) {
            	if ( msg.status === 'fail' ) {
            		alert(msg.data.msg);
            		window.location.href='Admin/login';
            	} else if ( msg.status === 'success' ) {
            		alert(msg.data.name);
            	}
            });
</script>
</head>
<body>
	<%%>

</body>
</html>