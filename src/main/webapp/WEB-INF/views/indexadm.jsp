<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="apple-touch-icon" sizes="76x76"	href="source/img/apple-icon.png" media="screen">
	<link rel="icon" type="image/png" href="source/img/favicon.png"	media="screen">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>BOA Salas de Atendimento</title>

	<meta	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'	name='viewport' />

	<!--     Fonts and icons     -->
		<link href="resources/css/material-icons.css" rel="stylesheet"	media="screen">
	<link rel="stylesheet" type="text/css"	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" />
	<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />

	<!-- CSS Files -->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet"	media="screen">
	<link href="resources/css/material-kit.css" rel="stylesheet"	media="screen">

	<!-- ANGUALR JS -->
	<script src ="./resources/js/angular.min.js"></script>
	<script src ="./resources/controller/controller.js"></script>

</head>
<body ng-app="app" ng-controller="appCtrl">
	<nav class="navbar navbar-transparent navbar-fixed-top navbar-color-on-scroll">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">BOA Salas de Atendimento</a>
				<!-- <img src="./resources/img/boa/logoboa.png" alt="Circle Image" class="img-raised img-responsive" style="height: 100px"> -->
			</div>

			<div class="collapse navbar-collapse" id="navigation-example">
				<ul class="nav navbar-nav navbar-right">
					<li ng-if="!usuarioLogado"><a href="#" data-toggle="modal" data-target="#myModal"> LOGIN ADM </a></li>
				</ul>
			</div>
		</div>
	</nav>

<div class="wrapper">
	<div class="header header-filter" style="background-image: url('resources/img/examples/city.jpg'); min-height: 200px"></div>
	<div class="main main-raised">
		<div class="profile-content">
			<div class="container" style="padding-bottom: 10%;">
				<h1></h1>
					<div class="row" style="margin: auto; display:flex;">
						<div class="col-xs-12 col-sm-12 col-md-9" style="margin: auto; display:flex;">
							<h1 style="margin: auto;">ADMINISTRATIVO</h1>
						</div>
					</div>
					<br /><br /><br />
					<div class="row" style="margin: auto; display:flex;">
						<div class="col-xs-12 col-sm-12 col-md-12" >
							<ul class="nav nav-pills nav-pills-primary" role="tablist" >
								<li>
									<a href="#clientes" role="tab" data-toggle="tab">
										<i class="material-icons">people</i>
										Clientes
									</a>
								</li>
								<li>
									<a href="#relatorio" role="tab" data-toggle="tab">
										<i class="material-icons">schedule</i>
										Relatório de agendamentos
									</a>
								</li>
								<li>
									<a href="#filiais" role="tab" data-toggle="tab">
										<i class="material-icons">domain</i>
										Filiais & Salas
									</a>
								</li>
							</ul>
						</div>
					</div>
					
					<div class="row" style="margin: auto; display:flex;">
						<div class="tab-content">

							<div id="clientes" class="tab-pane fade">
								<jsp:include page="relatorioclientes.jsp"></jsp:include>
							</div>
						
							<div id="relatorio" class="tab-pane fade">
								<jsp:include page="relatorioagendamentos.jsp"></jsp:include>
							</div>
							
							<div id="filiais" class="tab-pane fade">
								<jsp:include page="filiais.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<footer class="footer">
	<div class="container">
		<nav class="pull-left">
			<ul>
				<li><a href="http://www.creative-tim.com"> Creative Tim </a></li>
				<li><a href="http://presentation.creative-tim.com"> About Us </a></li>
				<li><a href="http://blog.creative-tim.com"> Blog </a></li>
				<li><a href="http://www.creative-tim.com/license"> Licenses	</a></li>
			</ul>
		</nav>
		<div class="copyright pull-right">
			&copy; 2016
			<i class="fa fa-heart heart"></i> by WhileTrue
		</div>
	</div>
</footer>

</body>

<!--   Core JS Files   -->
<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
<script src="resources/js/material.min.js"></script>

<!--  Plugin for the Sliders, full documentation here: http://refreshless.com/nouislider/ -->
<script src="resources/js/nouislider.min.js" type="text/javascript"></script>

<!--  Plugin for the Datepicker, full documentation here: http://www.eyecon.ro/bootstrap-datepicker/ -->
<script src="resources/js/bootstrap-datepicker.js" type="text/javascript"></script>

<!-- Control Center for Material Kit: activating the ripples, parallax effects, scripts from the example pages etc -->
<script src="resources/js/material-kit.js" type="text/javascript"></script>

</html>