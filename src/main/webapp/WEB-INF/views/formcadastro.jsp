<form action="cadastrar" method="POST">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-6">
			<div class="form-group label-floating" id="divNome">
				<label class="control-label">Nome Completo</label> 
				<input required type="text" class="form-control" ng-model="cliente.nome" ng-blur="verificaCampoVazio(cliente.nome, 'divNome')"> 

			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-6">
			<div class="form-group label-floating" id="divSobreNome">
				<label class="control-label">Sobrenome</label> 
				<input required type="text" class="form-control" ng-model="cliente.sobrenome" ng-blur="verificaCampoVazio(cliente.sobrenome, 'divSobreNome')"> 

			</div>
		</div>		
	</div>
	
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-6">
			<div class="form-group label-floating" id="divEmail">
				<label class="control-label">Email</label> 
				<input required type="text" class="form-control" ng-model="cliente.email" ng-blur="verificaCampoVazio(cliente.email, 'divEmail')" />
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-6">
			<div class="form-group label-floating" id="divConfirmaEmail">
				<label class="control-label">Confirmar Email</label> 
				<input required	type="text" class="form-control" ng-model="confirmaEmail" ng-blur="verificaCampoVazio(confirmaEmail, 'divConfirmaEmail')"/> 
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="form-group label-floating" id="divSenha">
			<label class="control-label">Senha</label> 
			<input required type="password" class="form-control" ng-model="cliente.autenticacao.senha" ng-blur="verificaCampoVazio(cliente.autenticacao.senha, 'divSenha')"/> 
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="form-group label-floating" id="divConfirmarSenha">
			<label class="control-label">Confirmar Senha</label> 
			<input required type="password" class="form-control" ng-model="confirmaSenha" ng-blur="verificaCampoVazio(confirmaSenha, 'divConfirmarSenha')"/> 
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="form-group label-floating" id="divTelFixo">
			<label class="control-label">Telefone Fixo</label>
			<input id="telefonefixo" required type="text" class="form-control" ng-model="cliente.telFixo" ng-blur="verificaCampoVazio(cliente.telFixo, 'divTelFixo')"/>
		</div>
	</div>
	
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="form-group label-floating" id="divTelCelular">
			<label class="control-label">Telefone Celular</label>
			<input id="telefonecelular" required type="text" class="form-control" ng-model="cliente.telCelular" ng-blur="verificaCampoVazio(cliente.telCelular, 'divTelCelular')"/>
		</div>
	</div>	
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="form-group label-floating" id="divCpf">
			<label class="control-label">CPF</label> 
			<input id="cpf" required type="text" class="form-control" ng-model="cliente.cpf" ng-blur="verificaCampoVazio(cliente.cpf, 'divCpf')"/> 
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-3">
		<div class="form-group">
			<select required class="form-control" ng-model="cliente.sexo" id="selectSexo">
				<option value="M">Masculino</option>
				<option value="F">Feminino</option>
			</select>
		</div>
	</div>
</div>

<div class="modal-footer">
	<!-- <button type="button" class="btn btn-default btn-simple" data-dismiss="modal">CANCELAR</button> -->
			<button ng-click="cadastrarCliente(cliente)" type="button" class="btn btn-info btn-simple">SALVAR</button>
		</div>
</form>
		