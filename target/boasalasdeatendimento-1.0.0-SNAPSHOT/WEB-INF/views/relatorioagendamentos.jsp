
<div class="container">
	<h1></h1>
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#menu1">Relatório
				de Agendamentos</a></li>
	</ul>
	<div class="row" style="margin: auto;">

		<div class="col-xs-12 col-sm-12 col-md-12" style="margin: auto;">

			<div class="col-xs-12 col-sm-12 col-md-2">
				<div class="form-group">
					<label>Data inicio</label> 
					<input value="${dataAtual}"  id="dataInicio" class="datepicker form-control" type="text"/>
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-2">
				<div class="form-group">
					<label>Data fim</label> 
					<input value="${dataAtual}"  id="dataFim" class="datepicker form-control" type="text"/>
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-2">
				<div class="form-group">
					<label>Status</label> 
					<select class="form-control" id="status">
						<option value="0">Todos</option>
						<option value="1">Aberto</option>
						<option value="2">Finalizado</option>
						<option value="3">Cancelado</option>
					</select>
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-2">
				<div class="form-group">
					<label>Cliente</label> 
					<input type="text" class="form-control" ng-model="pesquisaCliente">
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-2">
				<div class="form-group">
					<button type="button" class="btn btn-success" ng-click="gerarRelatorio()">Buscar</button>
				</div>
			</div>	
			<div class="col-xs-12 col-sm-12 col-md-2">
				<div class="form-group">
				</div>
			</div>		
		</div>
	</div>

	<div class="row">
		<div class="card card-nav-tabs" style="margin-top: 10px;">
				<span class="loader" id="loader"></span>
			<div class="card-body ">
				<div class="col-xs-12 col-sm-12 col-md-12">
					<div style="overflow: auto; height: 345px;">
				
						<table class="table" style="margin-left: auto; margin-right: auto; overflow: scroll;">
							<thead>
								<tr>
									<th>Código</th>
									<th>Cliente</th>
									<th>Data</th>
									<th>Horário</th>
									<th>Unidade</th>
									<th>Sala</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="agendamento in agendamentosSerch = (agendamentos | filter:pesquisaCliente)">
									<td>{{agendamento.id}}</td>
									<td>{{agendamento.cliente.nome}}</td>
									<td>{{agendamento.dataAgendamentoString}}</td>
									<td>{{agendamento.horario.horarioString}}</td>
									<td>{{agendamento.sala.unidade.nomeUnidade}}</td>
									<td>SALA {{agendamento.sala.numero}}</td>
									<td ng-if="agendamento.status != 'ABERTO'">{{agendamento.status}}</td>
									<td ng-if="agendamento.status == 'ABERTO'">
										<a href="#" ng-click="cancelarAgendamento(agendamento.id, '${cliente.id}')" type="button" style="border: 1px solid purple; padding: 5px">
											Cancelar
										</a> &nbsp;    
										<a href="#" ng-click="finalizarAgendamento(agendamento.id, '${cliente.id}')" type="button" style="border: 1px solid green; padding: 5px; color: green">
											FInalizar
										</a>									
									</td>	
								</tr>
								<tr><td><h3>Total agendamentos: {{agendamentosSerch.length}} |<a ng-if = "agendamentos.length" href="./downloadcsv" type="button" class="btn btn-success btn-xs"> Baixar CSV</a></h3></td></tr>
							</tbody>
						</table>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</div>