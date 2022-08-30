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
		<div class="header header-filter" 
			style="background-image: url('resources/img/examples/city.jpg'); min-height: 140px; background-color: rgba(47, 46, 46, 0.63);"></div>
		<div class="main main-raised">
			<div class="profile-content">
				<div class="container" style="padding-bottom: 5%;">
					<h1></h1>
					<ul class="nav nav-tabs">
						<li><a data-toggle="tab" href="#menu1" ng-if="usuarioLogado" ng-click="carregarSalas()">Agendamento</a></li>
						<li class="active"><a data-toggle="tab" href="#menu2">Informações</a></li>
					</ul>
					
					<div class="tab-content" >
						<div id="menu1" class="tab-pane fade" ng-if="usuarioLogado">
							
							<div class="row">
							
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
															  <li ng-repeat="sala in unidade.listaSala"><a href="#tab{{sala.numero}}" ng-click="carregarHorarios(sala.numero, sala.id, sala.descricao )" data-toggle="tab" 
															  data-toggle="tooltip" data-placement="top" title="{{sala.descricao}}">SALA {{sala.numero}}</a></li>
															</ul>
														</div>										
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div class="col-xs-12 col-sm-12 col-md-8" >
									<div class="card card-nav-tabs">
										<span class="loader" id="loader"></span>
										<div class="card-body">
										<h3 ng-if="numeroSala" style="text-align: center;">Sala {{numeroSala}} - Grade de horários {{dataSelecionada}}</h3>
										<h4 ng-if="descricaoSala"><strong>Descrição da Sala:</strong> {{descricaoSala}}</h4>
										<br />
										<p>{{mensagemHorarios}}</p>
										<div style="overflow: auto; height: 345px;">
											<table class="table">
												<tbody ng-repeat="horario in listaHorario" style="overflow: scroll;">
													<tr>
														<td class="text-center">{{horario.horarioString}}</td>
														<td>Horário disponível</td>
														<td><a ng-attr-id="{{ 'btn-' + horario.id }}" class="btn btn-success btn-xs" ng-click="realizarAgendamento(horario.id, '${cliente.id}')">Agendar</a></td>
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
											o cancelamento com 24 horas de antecedência da data de sua reserva. A baixo a descrição de preços e 
											modalidades de contratação:</p>
											<br />
											<h3>Modalidades de contratação e preços</h3>
										</div>
									</div>
									
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12">
											<p style="text-align: justify;">
											<strong>Pacote de horas:</strong> Reserva mensal de no mínimo 10 horas,
											neste pacote o profissional poderá administrar seus horários de acordo com a sua demanda e disponibilidade das salas,
											não envolve compromisso de horários fixos e o custo de cada hora ficará no valor de R$30,00,
											devendo o pagamento ser acertado após o fechamento do mês e com o prazo de até 5 dias para o depósito em conta corrente.</p>
											<br />
											<p style="text-align: justify;">
											<strong>Hora Avulsa:</strong> Sublocação de salas por hora, cada hora custará R$35,00 ,
											independente do período e dia da reserva. Esta modalidade não envolve compromisso e o pagamento será feito no começo do mês seguinte,
											com prazo de até 5 dias para o depósito em conta corrente indicada no relatório de fechamento que será enviado ao profissional no termino de cada mês. </p>
										</div>
										
										<div class="col-xs-12 col-sm-12 col-md-12">
											<p style="text-align: justify;">
											<h3>Estrutura que compõe a contratação de sublocação de salas</h3>
											<p><strong> Copa: </strong>O profissional poderá usufruir de toda a estrutura da copa que é composta por aguá mineral, chás, bolachas, microondas, utensílios domésticos, materiais de higiene e máquina de café dolce gusto (cada profissional deverá se responsabilizar por trazer suas cápsulas ).</p>
											
											<p><strong>Banheiro:</strong> O banheiro é de uso dos pacientes e profissionais e estará sempre equipado com itens de Higiene.</p>
											
											<p><strong>Complexo de lojas:</strong> o condomínio é composto por uma excelente rede de apoio, no térreo temos os serviços de conveniência, cafeteria, bistrô e serviços de beleza e no piso -2 temos os serviços de farmácia e alimentação com ótimos restaurantes.</p>
											
											<p><strong>Recepção interna e externa:</strong> o prédio possui uma recepção que funciona 24 horas o que facilita a identificação e comunicação entre pacientes e profissionais. O profissional pode optar por autorizar seu paciente a subir imediatamente, após ser comunicado de sua chegada, deixando ele aguardando na recepção interna do conjunto da clínica, ou então, pedir para que ele aguarde na recepção externa que fica no térreo do prédio.</p> 
											
											<p><strong>Segurança 24 horas: </strong>o prédio comercial oferece segurança 24 horas e tranquilidade para nosso ambiente profissional. </p>
											
											<p><strong>Vallet: </strong>o serviço de vallet é oferecido por empresa credenciada ao condomínio, o carro fica guardado no próprio prédio, oferecendo comodidade e bom atendimento (custo pago diretamente a empresa que gerencia o estacionamento).</p>
											
											<p><strong>Agenda virtual: </strong>facilidade de manejo da agenda para verificar disponibilidade das salas através do acesso virtual.</p>
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