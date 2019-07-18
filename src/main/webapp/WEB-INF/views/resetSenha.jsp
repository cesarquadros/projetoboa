<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="apple-touch-icon" sizes="76x76"	href="source/img/apple-icon.png" media="screen">
	<link rel="icon" type="image/png" href="source/img/favicon.png"	media="screen">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Salas de atendimento</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'	name='viewport' />

	<!-- Fonts and icons -->
	<link rel="stylesheet"	href="https://fonts.googleapis.com/icon?family=Material+Icons" />
	<link rel="stylesheet" type="text/css"	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" />
	<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />

	<!-- CSS Files -->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet"	media="screen">
	<link href="resources/css/material-kit.css" rel="stylesheet"	media="screen">
	<link href="resources/css/loader.css" rel="stylesheet"	media="screen">
	
	
	<!-- ANGULAR JS -->
	<script src=./resources/js/angular.min.js></script>
	<script src="./resources/controller/controller.js"></script>

</head>
<body ng-app="app" ng-controller="appCtrl">

	<!--   Menu   -->
	<jsp:include page="cabecalho.jsp"></jsp:include>
	
	<div class="wrapper">
		<div class="header header-filter" style="background-image: url('resources/img/examples/city.jpg'); min-height: 140px"></div>
		<div class="main main-raised">
			<div class="profile-content">
				<div class="container" style="padding-bottom: 5%; padding-top: 5%">
					<h1></h1>
					
				<div class="row">
					<div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
						<div class="card card-signup">
							<span class="loader" id="loader"></span>
							<h4 class="text-divider"><strong>Esqueci minha senha</strong></h4>
							<div class="content">
								<div class="input-group">
									<br />
									<p>Digite seu CPF de cadastro abaixo e clique em enviar.
										Nós lhe enviaremos uma senha temporaria.</p>
								</div>
								<p>{{msg}}<p>

								<div class="form-group label-floating" id="divCpf">
									<label class="control-label">RG</label> 
									<input required id="cpf2" type="text" class="form-control" name="cpf" ng-model="cpf"/> 
								</div>
							</div>
							<div class="footer text-center">
								<a type=button" class="btn btn-primary btn-md" style="padding-left: 80px; padding-right: 80px" ng-click="resetSenha(cpf)">
								Enviar</a>
							</div>
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