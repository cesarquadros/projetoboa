<div class="row">

	<div class="col-xs-12 col-sm-12 col-md-6">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12">
				<form class="form" name="mainForm" method="post" ng-submit="cadastrarCliente(cliente, confirmaSenha)">
					<fieldset>
						<legend>Dados pessoais</legend>
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-4">
								<div class="form-group " id="divNome">
									<label class="control-label">Nome</label> 
									<input disabled type="text" class="form-control" name="nome" value="Cesar"> 
					
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-4">
								<div class="form-group " id="divSobreNome">
									<label class="control-label">Sobrenome</label> 
									<input disabled type="text" class="form-control" name="sobrenome" value="Quadros"> 
								</div>
							</div>		
							
							<div class="col-xs-12 col-sm-12 col-md-4">
								<div class="form-group  " >
									<label class="control-label">Sexo</label> 
									<input disabled type="text" class="form-control" name="sobrenome"  value="Masculino"> 
								</div>
							</div>		
						</div>
						
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-4">
								<div class="form-group" id="divCpf">
									<label class="control-label">CPF</label> 
									<input disabled id="cpf" type="text" class="form-control" name="cpf" value="37765289829"/> 
								</div>
							</div>		
							<div class="col-xs-12 col-sm-12 col-md-4">
								<div class="form-group " id="divTelFixo">
									<label class="control-label">Telefone Fixo</label>
									<input required id="telefonefixo" required type="text" name="telFixo" class="form-control" ng-model="cliente.telFixo" ng-blur="verificaCampoVazio(cliente.telFixo, 'divTelFixo')"/>
								</div>
							</div>
							
							<div class="col-xs-12 col-sm-12 col-md-4">
								<div class="form-group " id="divTelCelular">
									<label class="control-label">Celular</label>
									<input required id="telefonecelular" type="text" class="form-control" name="telCelular" ng-model="cliente.telCelular" ng-blur="verificaCampoVazio(cliente.telCelular, 'divTelCelular')"/>
								</div>
							</div>				
						</div>
					
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-4">
								<div class="form-group " id="divEmail">
									<label class="control-label">Email</label> 
									<input required type="email" class="form-control" name="email" ng-model="cliente.email" ng-blur="verificaCampoVazio(cliente.email, 'divEmail')" />
								    <span ng-class="error" ng-show="mainForm.email.$error.email"> Formato do e-mail é inválido</span>
									
								</div>
							</div>
						</div>
					
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<div class="col-xs-12 col-sm-12 col-md-8">
									<button type="submit" class="btn btn-success" ng-disabled="mainForm.$invalid">SALVAR ALTERAÇÕES</button>
								</div>
							</div>	
						</div>
					</fieldset>
				</form>			
			</div>
		</div>
	</div>
	
	
		<div class="col-xs-12 col-sm-12 col-md-1">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12">
	</div>
	</div></div>


	<div class="col-xs-12 col-sm-12 col-md-5">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12">
				<form class="form" name="mainForm2" method="post" ng-submit="editarSenha('${cliente.autenticacao.id}')">
					<fieldset>
						<legend>Alterar senha</legend>
						<div class="col-xs-12 col-sm-12 col-md-4">
							<div class="form-group " id="divSenha">
								<label class="control-label">Senha Atual</label> 
								<input required type="password" class="form-control" name="senha" ng-model="autenticacao.senha" ng-blur="verificaCampoVazio(autenticacao.senha, 'divSenha')"/> 
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-4">
							<div class="form-group" id="novaSenha">
								<label class="control-label">Nova Senha</label> 
								<input required type="password" class="form-control" ng-model="autenticacao.novaSenha"/> 
								<span ng-class="error" ng-show="result" style="color: red;"> Senhas não conferem</span>
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-4">
							<div class="form-group" id="divConfirmarSenha">
								<label class="control-label">Confirmar Senha</label> 
								<input required type="password" class="form-control" ng-model="autenticacao.confirmaNovaSenha"/> 
								<span ng-class="error" ng-show="result" style="color: red;"> Senhas não conferem</span>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<div class="col-xs-12 col-sm-12 col-md-8">
									<button type="submit" class="btn btn-success" ng-disabled="mainForm2.$invalid">ALTERAR SENHA</button>
								</div>
							</div>	
						</div>	
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>
		