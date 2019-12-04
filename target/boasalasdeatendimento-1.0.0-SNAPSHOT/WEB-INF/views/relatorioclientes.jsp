<div class="container">
	<h1></h1>
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#menu1">Relatório
				de Clientes</a></li>
	</ul>

	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-9">

			<form>
				<div class="col-xs-12 col-sm-12 col-md-6">
					<div class="form-group">
						<label>Filtro</label> <input type="text" class="form-control"
							placeholder="Digite o nome, cpf ou email do cliente" ng-model="pesqCli">
					</div>
				</div>
			</form>
		</div>
	</div>

	<div class="row">
		<div class="card card-nav-tabs" style="margin-top: 5px;">
			<span class="loader" id="loader-cliente"></span>
			<div class="card-body ">
				<div class="col-xs-12 col-sm-12 col-md-12">
					<table class="table">
						<thead>
							<tr>
								<th>Nome</th>
								<th>RG</th>
								<th>Email</th>
								<th>Telefone fixo</th>
								<th>Celular</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="cliente in relatorioClientes | filter: pesqCli">
								<td>{{cliente.nome}} {{cliente.sobrenome}}</td>
								<td>{{cliente.cpf}}</td>
								<td>{{cliente.email}}</td>
								<td>{{cliente.telFixo}}</td>
								<td>{{cliente.telCelular}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
