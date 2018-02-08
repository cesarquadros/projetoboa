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
			<a href="./index">
				<img style="width: 5%;" src="./resources/img/boa/logo_boa_branco.png" alt="Creative Tim Logo">
			</a>
			
		</div>

		<div class="collapse navbar-collapse" id="navigation-example" ng-init="verificarLogin('${cliente.nome}')">
			<ul class="nav navbar-nav navbar-right">
				<li ng-if="!usuarioLogado"><a href="#" data-toggle="modal"
					data-target="#myModal"> Cadastrar </a></li>
				<li ng-if="!usuarioLogado"><a href="login">Efetuar login</a></li>
				<li ng-if="usuarioLogado">
					<div class="col-md-3 dropdown">
						<a href="#" class="btn btn-simple dropdown-toggle"
							data-toggle="dropdown" style="color: white">Seja bem vindo ${cliente.nome} <b
							class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href=meusagendamentos>Meus agendamentos</a></li>
							<li><a href="#">Alterar senha</a></li>
							<li><a href="#" data-toggle="modal" data-target="#myModal">Alterar
									dados</a></li>
							<li class="divider"></li>
							<li><a href="/boasalasdeatendimento/logout">Sair</a></li>
						</ul>
					</div>
				</li>
			</ul>
		</div>
	</div>
</nav>
