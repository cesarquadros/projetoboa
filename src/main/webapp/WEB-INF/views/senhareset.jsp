<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="apple-touch-icon" sizes="76x76" href="source/img/apple-icon.png" media="screen">
	<link rel="icon" type="image/png" href="source/img/favicon.png" media="screen">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Login - Salas de Atendimento</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />

	<!--     Fonts and icons     -->
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />

	<!-- CSS Files -->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="resources/css/material-kit.css" rel="stylesheet" media="screen">
	
	<script src="resources/js/mask.js"></script>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.js" type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.min.js" type="text/javascript"></script>
	
	
</head>
<body class="signup-page">
	<nav class="navbar navbar-transparent navbar-absolute">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="./">Salas de atendimento</a>
			</div>

			<div class="collapse navbar-collapse" id="navigation-example">
				<ul class="nav navbar-nav navbar-right">
					<li>
						<a href="./novocadastro">
							Cadastrar
						</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="wrapper">
		<div class="header header-filter" style="background-image: url('resources/img/city.jpg'); background-size: cover; background-position: top center;">
			<div class="container">
				<div class="row">
					<div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
						<div class="card card-signup">
							<form class="form" method="post" action="./autenticar">
								<div class="header header-primary text-center">
									<h4>Salas de atendimento</h4>
								</div>
								<p class="text-divider"><strong>Esqueci minha senha</strong></p>
								<div class="content">

									<div class="input-group">
										<p>Digite seu e-mail de cadastro abaixo e clique em enviar.
											Nós lhe enviaremos um e-mail com link para recadastrar sua senha.</p>
									</div>

									<div class="form-group label-floating" id="divCpf">
										<label class="control-label">CPF</label> 
										<input required id="cpf" required type="text" class="form-control" name="cpf" ng-model="cliente.cpf" ng-blur="verificaCampoVazio(cliente.cpf, 'divCpf')"/> 
									</div>
								</div>
								<div class="footer text-center">
									<a type="button" class="btn btn-primary btn-md" style="padding-left: 80px; padding-right: 80px">Enviar</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>

			<footer class="footer">
				<div class="container">
					<nav class="pull-left">
						<ul>
							<li>
								<a href="#">
									Ninox Solutions
								</a>
							</li>
							<li>
								<a href="#">
									Sobre
								</a>
							</li>
							 <li>
								<a href="#" target="_blank" class="btn btn-simple btn-white btn-just-icon">
									<i class="fa fa-twitter"></i>
								</a>
							</li>
							<li>
								<a href="#" target="_blank" class="btn btn-simple btn-white btn-just-icon">
									<i class="fa fa-facebook-square"></i>
								</a>
							</li>
							<li>
								<a href="#" target="_blank" class="btn btn-simple btn-white btn-just-icon">
									<i class="fa fa-instagram"></i>
								</a>
							</li>
							 
						</ul>
					</nav>
					<div class="copyright pull-right">
						&copy; 2017 <i class="fa fa-heart heart"></i> by <a href="#" target="_blank">Ninox</a>
					</div>
				</div>
			</footer>
		</div>
	</div>
<c:remove scope="session" var="mensagemErro" />
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