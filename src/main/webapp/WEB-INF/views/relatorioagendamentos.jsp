
<div class="container">
	<h1></h1>
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#menu1">Relatório
				de Agendamentos</a></li>
	</ul>
	<div class="row" style="margin: auto;">

		<div class="col-xs-12 col-sm-12 col-md-9" style="margin: auto;">

			<form>
				<div class="col-xs-12 col-sm-12 col-md-3">
					<div class="form-group">
						<label>Data inicio</label> <input id="data"
							class="datepicker form-control" type="text" />
					</div>
				</div>

				<div class="col-xs-12 col-sm-12 col-md-3">
					<div class="form-group">
						<label>Data fim</label> <input id="datafim"
							class="datepicker form-control" type="text" />
					</div>
				</div>

				<div class="col-xs-12 col-sm-12 col-md-3">
					<div class="form-group">
						<label>Status</label> <select class="form-control"
							id="exampleFormControlSelect1">
							<option></option>
							<option>Reservado</option>
							<option>Cancelado</option>
							<option>Finalizado</option>
						</select>
					</div>
				</div>

				<div class="col-xs-12 col-sm-12 col-md-3">
					<div class="form-group">
						<label>Cliente</label> <input type="text" class="form-control">
					</div>
				</div>
			</form>
		</div>
	</div>

	<div class="row">
		<div class="card card-nav-tabs">
			<div class="card-body ">

				<div class="col-xs-12 col-sm-12 col-md-12">
					<table class="table" style="margin-left: auto; margin-right: auto;">
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
							<tr>
								<td>1</td>
								<td>João</td>
								<td>01/01/2018</td>
								<td>13:00</td>
								<td>Missionaria</td>
								<td>SALA 10</td>
								<td>Finalizado</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>