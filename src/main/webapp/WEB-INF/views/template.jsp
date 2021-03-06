<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="apple-touch-icon" sizes="76x76"	href="source/img/apple-icon.png" media="screen">
	<link rel="icon" type="image/png" href="source/img/favicon.png"	media="screen">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Salas de Atendimento</title>

	<meta	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'	name='viewport' />

	<!--     Fonts and icons     -->
	<link rel="stylesheet"	href="https://fonts.googleapis.com/icon?family=Material+Icons" />
	<link rel="stylesheet" type="text/css"	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" />
	<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />

	<!-- CSS Files -->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet"	media="screen">
	<link href="resources/css/material-kit.css" rel="stylesheet"	media="screen">

	<!-- ANGUALR JS -->
	<script src=./resources/js/angular.min.js></script>
	<script src="./resources/controller/controller.js"></script>

</head>
<body ng-app="app" ng-controller="appCtrl">
	<nav class="navbar navbar-transparent navbar-fixed-top navbar-color-on-scroll">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index">BOA Salas de Atendimento</a>
			</div>

			<div class="collapse navbar-collapse" id="navigation-example">
				<ul class="nav navbar-nav navbar-right">
					<li ng-if="!usuarioLogado"><a href="#" data-toggle="modal" data-target="#myModal"> Cadastrar </a></li>
					<li ng-if="!usuarioLogado"><a href="login">Efetuar login</a></li>
					<li ng-if="usuarioLogado">
						<div class="col-md-3 dropdown">
							<a href="#" class="btn btn-simple dropdown-toggle" data-toggle="dropdown" style="color: white"> Usuário logado <b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li><a href=#>Meus agendamentos</a></li>
								<li><a href="#">Alterar senha</a></li>
								<li><a href="#"  data-toggle="modal" data-target="#myModal">Alterar dados</a></li>
								<li class="divider"></li>
								<li><a href="#">Sair</a></li>
							</ul>
						</div>
					</li>
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
				<ul class="nav nav-tabs">
					<li><a data-toggle="tab" href="#menu1" ng-if="usuarioLogado">Agendamento</a></li>
					<li class="active"><a data-toggle="tab" href="#menu2">Informações</a></li>
				</ul>

				<div class="tab-content">

					<div id="menu1" class="tab-pane fade" ng-if="usuarioLogado">

						<h3>Agendamento</h3>
						<div class="row">
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
			&copy; 2016, made with <i class="fa fa-heart heart"></i> by Creative
			Tim
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