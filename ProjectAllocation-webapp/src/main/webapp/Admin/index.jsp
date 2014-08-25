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
<script src="http://d3js.org/d3.v2.js?2.9.1"></script>
<style>
.link {
	fill: none;
	stroke: #666;
	stroke-width: 1.5px;
}

.node circle {
	fill: #ccc;
	stroke: #fff;
	stroke-width: 1.5px;
}

text {
	font: 10px sans-serif;
	pointer-events: none;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row demo-row">
			<div class="span9">
				<div class="navbar navbar-inverse">
					<div class="navbar-inner">
						<div class="container">
							<button data-target="#nav-collapse-01" data-toggle="collapse"
								class="btn btn-navbar" type="button"></button>
							<div id="nav-collapse-01" class="nav-collapse collapse">
								<ul class="nav">
									<li class="active"><a href="#fakelink" id="adminname">
											Admin</a></li>
									<li class="disabled"><a href="#fakelink"> Professor</a>
										<ul>
											<li><a href="#fakelink">Add a professor</a></li>
											<li><a href="#fakelink">Delete a professor</a></li>
										</ul>
									<li class="disabled"><a href="#fakelink"> Student</a>
										<ul>
											<li><a href="#fakelink">Add a student</a></li>
											<li><a href="#fakelink">Delete a student</a></li>
										</ul>
									<li><a id="matching-btn"> Matching </a></li>
								</ul>
							</div>
							<!--/.nav -->
						</div>
					</div>
				</div>
			</div>
			<div class="span9" id="content">
				<p>Hello Administrator! test test test test test test test test
					test test test test test test test test test test test test test
					test test test test test test test test test test test test test
					test test test test test test test test test test</p>
			</div>
			<div class="span9">
				<a class="btn btn-large btn-block btn-danger" id="logout-btn"
					href="#fakelink">Log Out</a>
			</div>
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
function drawgraph(links) {

var nodes = {};

// Compute the distinct nodes from the links.
links.forEach(function(link) {
  link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});
  link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});
});


var width = 960,
    height = 500;

var force = d3.layout.force()
    .nodes(d3.values(nodes))
    .links(links)
    .size([width, height])
    .linkDistance(60)
    .charge(-300)
    .on("tick", tick)
    .start();
    

(nodes["fadf"] = {name:"fadf"});
force = d3.layout.force()
    .nodes(d3.values(nodes))
    .links(links)
    .size([width, height])
    .linkDistance(60)
    .charge(-300)
    .on("tick", tick)
    .start();

var svg = d3.select("#content").append("svg")
    .attr("width", width)
    .attr("height", height);

var link = svg.selectAll(".link")
    .data(force.links())
  .enter().append("line")
    .attr("class", "link");

var node = svg.selectAll(".node")
    .data(force.nodes())
  .enter().append("g")
    .attr("class", "node")
    .on("mouseover", mouseover)
    .on("mouseout", mouseout)
    .call(force.drag);

node.append("circle")
    .attr("r", 8);

node.append("text")
    .attr("x", 12)
    .attr("dy", ".35em")
    .text(function(d) { return d.name; });

function tick() {
  link
      .attr("x1", function(d) { return d.source.x; })
      .attr("y1", function(d) { return d.source.y; })
      .attr("x2", function(d) { return d.target.x; })
      .attr("y2", function(d) { return d.target.y; });

  node
      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
}

function mouseover() {
  d3.select(this).select("circle").transition()
      .duration(750)
      .attr("r", 16);
}

function mouseout() {
  d3.select(this).select("circle").transition()
      .duration(750)
      .attr("r", 8);
}
}
      $(function() {
        $("#matching-btn").on("click", function(){
           	$.ajax({
              type: "POST",
              url: "restServices/services/AdminService/matching/onestep",
              headers: {aid: $.cookie('aid'), session: $.cookie('session'), isFinished: false },
              beforeSend : function (){
              } 
            }).done(function( msg ) {
            	if ( msg.status === 'fail' ) {
            		alert(msg.data.msg);
            	} else if ( msg.status === 'success' ) {
					drawgraph(msg.data.result);
            	}
            });
        })
      });
</script>
</html>