<form class="form" name="mainForm" method="post" ng-submit="cadastrarCliente(cliente)">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-4">
			<div class="form-group label-floating" id="divNome">
				<label class="control-label">Nome Completo</label> 
				<input required type="text" class="form-control" name="nome" ng-model="cliente.nome" ng-blur="verificaCampoVazio(cliente.nome, 'divNome')" > 

			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-4">
			<div class="form-group label-floating" id="divSobreNome">
				<label class="control-label">Sobrenome</label> 
				<input required type="text" class="form-control" name="sobrenome" ng-model="cliente.sobrenome" ng-blur="verificaCampoVazio(cliente.sobrenome, 'divSobreNome')"> 
			</div>
		</div>		
		
		<div class="col-xs-12 col-sm-12 col-md-4">
			<div class="form-group  label-floating" >
			<label class="control-label">Sexo</label> 
				<select class="form-control" name="sexo" id="exampleFormControlSelect1" ng-model="cliente.sexo">
					<option value="M">Masculino</option>
					<option value="F">Feminino</option>
				</select>
				<span class="material-input"></span>
			</div>
		</div>		
		
	</div>
	
	<div class="row">

<!-- 		<div class="col-xs-12 col-sm-12 col-md-3">
			<div class="form-group label-floating">
				<label class="control-label">Escolha uma data</label> 
				<input id="data2" name="dataNascimentoString"class="datepicker form-control" type="text" value="${dataAtual}" readonly/> 
			</div>	
		</div>		 -->
		<div class="col-xs-12 col-sm-12 col-md-4">
			<div class="form-group label-floating" id="divCpf">
				<label class="control-label">CPF</label> 
				<input required id="cpf" required type="text" class="form-control" name="cpf" ng-model="cliente.cpf" ng-blur="verificaCampoVazio(cliente.cpf, 'divCpf')"/> 
			</div>
		</div>		
		<div class="col-xs-12 col-sm-12 col-md-4">
			<div class="form-group label-floating" id="divTelFixo">
				<label class="control-label">Telefone Fixo</label>
				<input required id="telefonefixo" required type="text" name="telFixo" class="form-control" ng-model="cliente.telFixo" ng-blur="verificaCampoVazio(cliente.telFixo, 'divTelFixo')"/>
			</div>
		</div>
		
		<div class="col-xs-12 col-sm-12 col-md-4">
			<div class="form-group label-floating" id="divTelCelular">
				<label class="control-label">Celular</label>
				<input required id="telefonecelular" type="text" class="form-control" name="telCelular" ng-model="cliente.telCelular" ng-blur="verificaCampoVazio(cliente.telCelular, 'divTelCelular')"/>
			</div>
		</div>				
	</div>

	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-4">
			<div class="form-group label-floating" id="divEmail">
				<label class="control-label">Email</label> 
				<input required type="email" class="form-control" name="email" ng-model="cliente.email" ng-blur="verificaCampoVazio(cliente.email, 'divEmail')" />
			    <span ng-class="error" ng-show="mainForm.email.$error.email"> Formato do e-mail é inválido</span>
				
			</div>
		</div>
<!-- 		<div class="col-xs-12 col-sm-12 col-md-3">
			<div class="form-group label-floating" id="divConfirmaEmail">
				<label class="control-label">Confirmar Email</label> 
				<input required	type="text" class="form-control" ng-model="confirmaEmail" ng-blur="verificaCampoVazio(confirmaEmail, 'divConfirmaEmail')"/> 
			</div>
		</div> -->
		<div class="col-xs-12 col-sm-12 col-md-4">
			<div class="form-group label-floating" id="divSenha">
				<label class="control-label">Senha</label> 
				<input required type="password" class="form-control" name="senha" ng-model="cliente.autenticacao.senha" ng-blur="verificaCampoVazio(cliente.autenticacao.senha, 'divSenha')"/> 
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-4">
			<div class="form-group label-floating" id="divConfirmarSenha">
				<label class="control-label">Confirmar Senha</label> 
				<input required type="password" class="form-control" ng-model="confirmaSenha" ng-blur="validaSenha(cliente.autenticacao.senha, confirmaSenha)"/> 
				<span ng-class="error" ng-show="result" style="color: red;"> Senhas não conferem</span>
			</div>
		</div>
	</div>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-12">
		<div class="modal-footer" style="text-align: center;">
			<div class="col-xs-12 col-sm-12 col-md-2">
				<button type="submit" class="btn btn-success" ng-disabled="mainForm.$invalid">SALVAR</button>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-2">
				<button type="reset" class="btn btn-default">LIMPAR</button>
			</div>
		</div>	
	</div>	
</div>
</form>
		