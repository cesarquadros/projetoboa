<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
	<link href="resources/css/loader.css" rel="stylesheet"	media="screen">

	<!-- ANGUALR JS -->
	<script src ="./resources/js/angular.min.js"></script>
	<script src ="./resources/controller/controller.js"></script>

</head>
<body ng-app="app" ng-controller="appCtrl">

<nav
	class="navbar navbar-transparent navbar-fixed-top navbar-color-on-scroll">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>

			<ul class="nav navbar-nav navbar-left">
				<li><h4><strong>Salas de atendimento</strong></h4></li>
			</ul>
			
		</div>

		<div class="collapse navbar-collapse" id="navigation-example">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="./index"> Pagina inicial </a></li>
			</ul>
		</div>
	</div>
</nav>

<div class="wrapper">
<div class="header header-filter" style="background-image: url('resources/img/examples/city.jpg'); min-height: 140px"></div>
	<div class="main main-raised">
		<div class="profile-content">
			<div class="container" style="padding-bottom: 3%;">
				<br />
				
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#menu1">Novo cadastro</a></li>
				</ul>	
				<div class="alert alert-warning" ng-if="erro">
				    <div class="container-fluid">
					  <div class="alert-icon">
						<i class="material-icons">warning</i>
					  </div>
					  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true"><i class="material-icons">clear</i></span>
					  </button>
				      <b>{{msgerro}}</b> 
				        <span ng-repeat="erro in listaErros">{{erro}} </span>
				    </div>
				</div>		
				<div class="card card-nav-tabs">
				<span class="loader" id="loader"></span>
					<div class="card-body ">
						{{carregando}}
						<div class="col-xs-12 col-sm-12 col-md-12" ng-if="!sucesso">			
							<jsp:include page="formcadastro.jsp"></jsp:include>				
						</div>
						
						<div class="col-xs-12 col-sm-12 col-md-12" ng-if="sucesso">			
							<h4>Cadastro realizado com sucesso! <a href="./index">Ir para pagína inicial</a></h4>			
						</div>
						
					</div>
				</div>				
			</div>
		</div>
	</div>
</div>

	<!--   Rodapé   -->
	<jsp:include page="rodape.jsp"></jsp:include>

</body>

<!--   Core JS Files   -->
<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/bootstrap.min.js" type="text/javascript"></script>
<script src="resources/js/material.min.js"></script>
<script src="resources/js/mask.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.min.js" type="text/javascript"></script>

<!--  Plugin for the Sliders, full documentation here: http://refreshless.com/nouislider/ -->
<script src="resources/js/nouislider.min.js" type="text/javascript"></script>

<!--  Plugin for the Datepicker, full documentation here: http://www.eyecon.ro/bootstrap-datepicker/ -->
<script src="resources/js/bootstrap-datepicker.js" type="text/javascript"></script>

<!-- Control Center for Material Kit: activating the ripples, parallax effects, scripts from the example pages etc -->
<script src="resources/js/material-kit.js" type="text/javascript"></script>

</html>