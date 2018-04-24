<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="apple-touch-icon" sizes="76x76"
	href="source/img/apple-icon.png" media="screen">
<link rel="icon" type="image/png" href="source/img/favicon.png"
	media="screen">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>BOA Salas de Atendimento</title>

<meta
	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'
	name='viewport' />

<!--     Fonts and icons     -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons" />
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />

<!-- CSS Files -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/material-kit.css" rel="stylesheet"
	media="screen">
<link href="resources/css/loader.css" rel="stylesheet" media="screen">

<!-- ANGUALR JS -->
<script src=./resources/js/angular.min.js></script>
<script src="./resources/controller/controller.js"></script>

</head>
<body ng-app="app" ng-controller="appCtrl">
	<!--   Menu   -->
	<jsp:include page="cabecalho.jsp"></jsp:include>

	<div class="wrapper" ng-init="meusAgendamentosById('${cliente.id}')">

		<div class="header header-filter"
			style="background-image: url('resources/img/examples/city.jpg'); min-height: 140px; background-color: rgba(47, 46, 46, 0.63);"></div>
		<div class="main main-raised">
			<div class="profile-content">
				<div class="container" style="padding-bottom: 5%;">
					<h1></h1>
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#menu1">Meus
								Agendamentos</a></li>
					</ul>

					<div>
						<div class="row" style="margin-top: 5px">

							<div class="card card-nav-tabs">
								<i class="loader" id="loader"></i>
								<div class="card-body ">
									<div class="col-xs-12 col-sm-12 col-md-12"
										style="overflow: auto; height: 350px;">
										<table class="table"
											style="margin-left: auto; margin-right: auto;">
											<thead>
												<tr>
													<th>Código</th>
													<th>Data</th>
													<th>Horário</th>
													<th>Unidade</th>
													<th>Sala</th>
													<th>Status</th>
												</tr>
											</thead>
											<tbody style="overflow: scroll;">
												<tr ng-repeat="agendamento in meusAgendamentos">
													<td>{{agendamento.id}}</td>
													<td>{{agendamento.dataAgendamentoString}}</td>
													<td>{{agendamento.horario.horarioString}}</td>
													<td>{{agendamento.sala.unidade.nomeUnidade}}</td>
													<td>SALA {{agendamento.sala.numero}}</td>
													<td ng-if="agendamento.status != 'ABERTO'">{{agendamento.status}}</td>
													<td ng-if="agendamento.status == 'ABERTO'"><a href="#"
														ng-click="cancelarAgendamento(agendamento.id, '${cliente.id}')"
														type="button"
														style="border: 1px solid purple; padding: 5px">
															Cancelar </a></td>
												</tr>
												<!-- 
												<tr>
												<td></td>
												<td>01/01/2018</td>
												<td>13:00</td>
												<td>SALA 10</td>
												<td><a href="#" type="button" style="border: 1px solid purple; padding: 5px">Cancelar</a></td>
											</tr> 
											-->
											</tbody>
										</table>
									</div>
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

<!--  Plugin for the Sliders, full documentation here: http://refreshless.com/nouislider/ -->
<script src="resources/js/nouislider.min.js" type="text/javascript"></script>

<!--  Plugin for the Datepicker, full documentation here: http://www.eyecon.ro/bootstrap-datepicker/ -->
<script src="resources/js/bootstrap-datepicker.js"
	type="text/javascript"></script>

<!-- Control Center for Material Kit: activating the ripples, parallax effects, scripts from the example pages etc -->
<script src="resources/js/material-kit.js" type="text/javascript"></script>

</html>