<nav
	class="navbar navbar-transparent navbar-fixed-top navbar-color-on-scroll">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#navigation-example">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<!-- Logotipo -->
			<ul class="nav navbar-nav navbar-left tamanho">
				<li><a href="./index" style="font-size: 20px;"><strong>Salas de atendimento</strong></a></li>
			</ul>
			
		</div>

		<div class="collapse navbar-collapse" id="navigation-example" ng-init="verificarLogin('${cliente.nome}')">
			<ul class="nav navbar-nav navbar-right">
				
				<li ng-if="!usuarioLogado"><a href="novocadastro"> <strong>Cadastrar</strong> </a></li>
				<li ng-if="!usuarioLogado"><a href="login"><strong>Efetuar login</strong></a></li>
				<li ng-if="usuarioLogado">
					<div class="col-md-3 dropdown">
						<a href="#" class="btn btn-simple dropdown-toggle"
							data-toggle="dropdown" style="color: white"><strong>Seja bem vindo ${cliente.nome}</strong> <b
							class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="meusagendamentos">Meus agendamentos</a></li>
							<li><a href="./meuperfil">Meu perfil</a></li>
							<li class="divider"></li>
							<li><a href="./logout">Sair</a></li>
						</ul>
					</div>
				</li>
			</ul>
		</div>
	</div>
</nav>
