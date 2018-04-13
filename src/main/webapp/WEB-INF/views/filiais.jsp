<div class="container">
	<h1></h1>
	<ul class="nav nav-tabs">
		<li class="active">
		<a data-toggle="tab" href="#menu1">Filiais & Salas</a>
		</li>
	</ul>

	<div class="row" style="text-align: right;">
		<button class="btn btn-primary btn-xs">Nova filial</button>
		<button class="btn btn-primary btn-xs">Nova sala</button>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-9">
			<div class="col-xs-12 col-sm-12 col-md-6">
				<div class="form-group">
					<label>Pesquisa Filial/Sala</label> <input type="text"
						class="form-control" placeholder="Digite o nome da filial ou sala">
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="card card-nav-tabs" style="margin-top: 5px;" >
			<span class="loader" id="loader-unidades"></span>
			<div class="card-body ">
				<div class="col-xs-12 col-sm-12 col-md-12">
					<table class="table">
						<thead>
							<tr>
								<th>Nome filial</th>
								<th>Endereço</th>
								<th>Quantidade de salas</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="unidade in unidades">
								<td>{{unidade.nomeUnidade}}</td>
								<td>{{unidade.endereco.logradouro}}, {{unidade.endereco.numero}} - {{unidade.endereco.bairro}}</td>
								<td>{{unidade.listaSala.length}}</td>
								<td>Ativo</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>