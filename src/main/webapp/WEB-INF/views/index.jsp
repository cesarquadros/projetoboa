<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="apple-touch-icon" sizes="76x76"	href="source/img/apple-icon.png" media="screen">
	<link rel="icon" type="image/png" href="source/img/favicon.png"	media="screen">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>BOA Salas de Atendimentos</title>

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
	
	
	<!--   Menu   -->
	<jsp:include page="cabecalho.jsp"></jsp:include>
	
	<!-- Modal Core -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Novo Cadastro</h4>
				</div>
				<div class="modal-body">
					<jsp:include page="formcadastro.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>

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
							<br /><br />
							<div class="row">
								<div class="col-xs-12 col-sm-12 col-md-3">
									<p>
									  <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
									    Filial Yervant
									  </a>
									</p>
									<div class="collapse" id="collapseExample">
										<h4>
											<a href="#" class="btn btn-default btn-xs" ng-click="setSala('1')"	ng-model="data" value="1">Sala 1</a> 
											<a href="#" class="btn btn-default btn-xs" ng-click="setSala('2')">Sala 2</a>
										</h4>
									</div>
									
									
									<p>
									  <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample2" role="button" aria-expanded="false" aria-controls="collapseExample">
									    Filial Interlagos
									  </a>
									</p>
									<div class="collapse" id="collapseExample2">
										<h4>
											<a href="#" class="btn btn-default btn-xs" ng-click="setSala('1')"	ng-model="data" value="1">Sala 1</a> 
											<a href="#" class="btn btn-default btn-xs" ng-click="setSala('2')">Sala 2</a>
										</h4>
									</div>								
									
									
								</div>
								<div class="col-xs-12 col-sm-12 col-md-4">
									<!-- markup -->
									<input id="data" class="datepicker form-control" type="text" value="${dataAtual}" /> 
									<a href="#" class="btn btn-primary btn-sm" style="float: right;" ng-click="testeBusca()"> Buscar</a>
								</div>
	
								<div class="col-xs-12 col-sm-12 col-md-4" ng-init="testeBusca()">
									<table class="table">
										<h3>Sala {{numeroSala}}</h3>
										<tbody ng-repeat="horario in listaHorario">
											<tr>
												<td class="text-center">{{horario.horario}}</td>
												<td>Horário disponível</td>
												<td><button class="btn btn-success btn-xs">Agendar</button></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
	
						<div id="menu2" class="tab-pane fade in active">
							<h3>Informações</h3>
							<p>Sed ut perspiciatis unde omnis iste natus error sit
							voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
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

<!--  Plugin for the Sliders, full documentation here: http://refreshless.com/nouislider/ -->
<script src="resources/js/nouislider.min.js" type="text/javascript"></script>

<!--  Plugin for the Datepicker, full documentation here: http://www.eyecon.ro/bootstrap-datepicker/ -->
<script src="resources/js/bootstrap-datepicker.js" type="text/javascript"></script>

<!-- Control Center for Material Kit: activating the ripples, parallax effects, scripts from the example pages etc -->
<script src="resources/js/material-kit.js" type="text/javascript"></script>

</html>