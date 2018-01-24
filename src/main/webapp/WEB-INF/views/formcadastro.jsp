<form action="/cadastrar" method="POST">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12">
			<div class="form-group label-floating" id="divNome">
				<label class="control-label">Nome Completo</label> 
				<input type="text" class="form-control" ng-model="nome" ng-blur="insereIcone = teste(nome)"> <span	class="form-control-feedback" ng-if="insereIcone"> <i
					class="material-icons">done</i>
				</span> <span class="material-icons form-control-feedback"	ng-if="insereIcone == false">clear</span>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-6">
			<div class="form-group label-floating has-success">
				<label class="control-label">Email</label> <input type="text" class="form-control" /> <span class="form-control-feedback">
					<i class="material-icons">done</i>
				</span>
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-6">
			<div class="form-group label-floating has-success">
				<label class="control-label">Confirmar Email</label> <input	type="text" class="form-control" /> <span class="form-control-feedback"> <i	class="material-icons">done</i>
			</span>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="form-group label-floating has-error">
			<label class="control-label">Senha</label> <input type="text" class="form-control" /> 
			<span class="form-control-feedback">
				<i class="material-icons">done</i>
			</span>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="form-group label-floating has-error">
			<label class="control-label">Confirmar Senha</label> 
			<input type="text" class="form-control" /> 
				<span class="form-control-feedback"> 
					<i class="material-icons">done</i>
			</span>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="form-group label-floating has-error">
			<label class="control-label">Telefone Celular</label> <input type="text" class="form-control" />
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="form-group label-floating has-error">
			<label class="control-label">CPF</label> <input	type="text" class="form-control" /> 
		</div>
	</div>
</div>
<div class="modal-footer">
	<!-- <button type="button" class="btn btn-default btn-simple" data-dismiss="modal">CANCELAR</button> -->
			<button type="button" class="btn btn-info btn-simple">SALVAR</button>
		</div>
</form>
		