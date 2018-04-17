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
				<a class="navbar-brand" href="http://www.creative-tim.com">Salas de atendimento</a>
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
									<h4>Faça seu Login</h4>
								</div>
								<p class="text-divider">BOA</p>
								<div class="content">

									<div class="input-group">
										<span class="input-group-addon">
											<i class="material-icons">email</i>
										</span>
										<input name="usuario" type="text" class="form-control" placeholder="Usuário">
									</div>

									<div class="input-group">
										<span class="input-group-addon">
											<i class="material-icons">lock_outline</i>
										</span>
										<input name="senha" type="password" placeholder="Senha" class="form-control" />
									</div>

									<!-- If you want to add a checkbox to this form, uncomment this code

									<div class="checkbox">
										<label>
											<input type="checkbox" name="optionsCheckboxes" checked>
											Subscribe to newsletter
										</label>
									</div> -->
								</div>
								<div class="footer text-center">
									<button type="submit" class="btn btn-simple btn-primary btn-lg">Entrar</button>
									<a href="#" class="btn btn-simple btn-primary btn-lg">Cancelar</a><br />
									<a href="#" class="btn btn-simple btn-primary btn-lg">Esqueci minha senha</a>
									<p style="color: red">${mensagemErro}</p>
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