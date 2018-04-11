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
				<div class="container" style="padding-bottom: 5%;">
					<h1></h1>
					<ul class="nav nav-tabs">
						<li><a data-toggle="tab" href="#menu1" ng-if="usuarioLogado">Agendamento</a></li>
						<li class="active"><a data-toggle="tab" href="#menu2">Informações</a></li>
					</ul>
					
					<div class="tab-content" >
						<div id="menu1" class="tab-pane fade" ng-if="usuarioLogado">
							
							<div class="row" ng-init="carregarSalas()">
							
								<div class="col-xs-12 col-sm-12 col-md-4" >
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12">
											<div class="card card-nav-tabs">
												<div class="card-body ">
													<!-- markup -->
													<b>Dica: </b>Escolha uma data, em seguida clique na sala
													<br />
													<div class="form-group label-floating">
														<label class="control-label">Escolha uma data</label> 
														<input id="data" class="datepicker form-control" type="text" value="${dataAtual}" readonly="readonly"/> 
													</div>
												</div>
											</div>
										</div>
									
									
										<div class="col-xs-12 col-sm-12 col-md-12">
											<div class="card card-nav-tabs">
												<div class="card-body ">
														<p>{{mensagemUnidade}}</p>
													<div ng-repeat="unidade in unidades" style="text-align: center;">
														<h4 >Unidade: {{unidade.nomeUnidade}}</h4>
				
														<div>
															<!-- simple and vertical -->
															<ul class="nav nav-pills nav-pills-rose nav-stacked">
															  <li ng-repeat="sala in unidade.listaSala"><a href="#tab{{sala.numero}}" ng-click="carregarHorarios(sala.numero, sala.id)" data-toggle="tab">SALA {{sala.numero}}</a></li>
															</ul>
														</div>										
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div class="col-xs-12 col-sm-12 col-md-8" ng-init="carregarHorarios(numeroSala, idSala)">
									<div class="card card-nav-tabs">
										<span class="loader" id="loader"></span>
										<div class="card-body">
										<h3 style="text-align: center;">Sala {{numeroSala}} - Grade de horários {{dataSelecionada}}</h3>
										<div style="overflow: auto; height: 345px;">
											<table class="table">
												
												<tbody ng-repeat="horario in listaHorario" style="overflow: scroll;">
													<tr>
														<td class="text-center">{{horario.horarioString}}</td>
														<td>Horário disponível</td>
														<td><a class="btn btn-success btn-xs" ng-click="realizarAgendamento(horario.id, '${cliente.id}')">Agendar</a></td>
													</tr>
												</tbody>
											</table>
											
											</div>
										</div>
									</div>
								</div>		
							</div>
						</div>
	
						<div id="menu2" class="tab-pane fade in active">
							<div class="card card-nav-tabs">
								<div class="card-body ">
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12" style="text-align: justify;">
											<h3>Salas de atendimento</h3>
											<p>Reserve sua sala com maior praticidade, basta escolher a sala, a data e o horário de seu atendimento, 
											verificar a disponibilidade e efetuar sua reserva. para cancelamento sem cobranças é importante processar 
											o cancelamento com 24 horas de antecedência da data de sua reserva. A baixo a descrição de preços e modalidades de contratação:</p>
											<br />
											<h3>Modalidades de contratação e preços</h3>
										</div>
									</div>
									
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12">
											<p style="text-align: justify;"><strong>Fixa:</strong> envolve compromisso, a sala fica reservada para o profissional no mesmo dia da semana e horários por todo o mês, 
											mesmo que você não use. O pagamento é antecipado e o profissional garante a exclusividade do horário e local. 
											Pagando R$5,00 mais barato em relação a hora avulsa. Nesta modalidade você conta com a facilidade de dois reagendamentos 
											por horário fixo desde que avisado que não irá utilizar a sala em determinado dia com 24 horas de antecedência e o horário do 
											reagendamento deve ser programado sempre no mesmo mês, de acordo com a disponibilidade de salas. O Pagamento é feito de maneira 
											antecipada garantindo a sua reserva mensal.</p>
											<p><strong>Valores:</strong> Período da manhã (08:00 as 11:00) R$20,00 Período da Tarde (12:00 as 16:00) R$25,00 
											Período Noturno (17:00 as 21:00) R$30,00</p>
											<br />
										</div>
										
										<div class="col-xs-12 col-sm-12 col-md-12">
											<p style="text-align: justify;"><strong>Avulsa:</strong> envolve comodidade, a sala fica reservada para o profissional somente 
											no dia e horário que ele solicitar esta reserva, as reservas devem ser feitas semanalmente de acordo com a disponibilidade das salas. 
											O cancelamento deve ser avisado com 24 horas uteis de antecedência ao horário agendado. O pagamento é feito no final do mês após a 
											utilização dos horários correspondentes.
											</p>
											<p><strong>Valores:</strong>Período da manhã (08:00 as 11:00) R$25,00 Período da Tarde (12:00 as 16:00) R$30,00 
											Período Noturno (17:00 as 21:00) R$35,00
											</p>
										</div>
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