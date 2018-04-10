
<nav class="navbar navbar-transparent navbar-fixed-top navbar-color-on-scroll">
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
			<ul class="nav navbar-nav navbar-left">
				<li><h4><strong>Salas de atendimento</strong></h4></li>
				<li><a href="./index">Inicio</a></li>
			</ul>

		</div>

		<div class="collapse navbar-collapse" id="navigation-example">
			<ul class="nav navbar-nav navbar-right">
				<li ng-if="!usuarioLogado"><a href="#" data-toggle="modal"
					data-target="#myModal"> <strong>LOGIN ADM</strong> </a></li>
			</ul>
		</div>
	</div>
</nav>