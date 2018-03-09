var app = angular.module('app', []);
app.controller('appCtrl', [ '$scope', '$http', '$timeout',function($scope, $http, $timeout) {
	$scope.numeroSala = 1;
	$scope.idSala = 1;
	$scope.listaHorario = [];
	$scope.unidades = [];
	$scope.meusAgendamentos = [];
	$scope.dataSelecionada;
	$scope.agendamento;
	$scope.statusAgendamento;
	$scope.cliente;
	$scope.listaErros = [];
	
/*	$scope.cadastrarCliente = function(cliente) {
		
		$http({
			method : 'post',
			url : '/boasalasdeatendimento/cadastrarcliente',
			data : JSON.stringify(cliente),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			$scope.unidades = retorno.data;
		});
	}*/
	
	//------------------------------------------------------------- Requisições ---------------------------------------------------
	$scope.realizarAgendamento = function(idHora, idCliente) {

		loader = angular.element( document.querySelector('#loader'));
		sucess = angular.element( document.querySelector('#sucess'));
		
        loader.addClass('loader-ativo');
		
		data = $scope.dataSelecionada;
		agendamento = {"horario" : {"id" : idHora}, 
						"dataAgendamentoString" : data, 
						"cliente" : {"id" : idCliente},
						"sala" : {"id" : $scope.idSala}};
		$http({
			method : 'post',
			url : './realizaragendamento',
			data : JSON.stringify(agendamento),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			$scope.statusAgendamento = retorno.data;
			
			$scope.carregarHorarios($scope.numeroSala, $scope.idSala);
	        loader.removeClass('loader-ativo');
		});
	}

	$scope.carregarSalas = function() {
		
		$scope.mensagemUnidade = 'Carregando...';
		
		json = { "id" : "id"};
		
		$http({
			method : 'post',
			url : './carregarsalas',
			data : JSON.stringify(json),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			$scope.unidades = retorno.data;
			$scope.mensagemUnidade = '';
		});
	}
	
	$scope.carregarHorarios = function(numeroSala, idSala) {
		
		$scope.numeroSala = numeroSala;
		$scope.dataSelecionada = document.getElementById('data').value;
		$scope.idSala = idSala;
		//newDate = new Date(data);
		consultaSala = { "sala" : $scope.idSala, "data" : $scope.dataSelecionada};
		
		$http({
			method : 'post',
			url : './carregarhorariodisponivel',
			data : JSON.stringify(consultaSala),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			$scope.listaHorario = retorno.data;
		});
	}
	
	$scope.meusAgendamentosById = function(idCliente) {
		
		json = { "id" : "id"};
		loader = angular.element( document.querySelector('#loader'));
		loader.addClass('loader-ativo');
		
		$http({
			method : 'post',
			url : './meusagendamentos/'+idCliente,
			data : JSON.stringify(json),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			var agendamentos =  retorno.data
			
			if(agendamentos == ""){
				loader.removeClass('loader-ativo');
				return false;
			} else {
				loader.removeClass('loader-ativo');
				$scope.meusAgendamentos = retorno.data;
				return true;
			}
		});
	}
	
	$scope.cancelarAgendamento = function(idAgendamento, idCliente) {
		
		json = { "id" : "id"};
		loader = angular.element( document.querySelector('#loader'));
		loader.addClass('loader-ativo');
		
		$http({
			method : 'post',
			url : './cancelaragendamento/'+ idAgendamento,
			data : JSON.stringify(json),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			var retorno =  retorno.statusText;
			
			if(retorno == "OK"){
				$scope.mensagem = true;
		        	
		        	$scope.mensagem = false;
		        	$scope.meusAgendamentosById(idCliente);
		        	loader.removeClass('loader-ativo');
				return true;
			} else {
				return false;
			}
		});
	}
	
	//-----------------------------------------------------FIM REQUISIÇÕES-----------------------------------------------------------

	
	
	//----------------------------------------------------------------------------------------------------------------
	$scope.verificaErros = function(erros){
		if(erros){
			$scope.listaErros = erros;
			return true;
		} else {
			return false;
		}
	}
	
	$scope.verificaCampoVazio = function(nome, div) {
		campo = angular.element( document.querySelector('#'+div));
		
		if(nome != undefined){
			campo.removeClass('has-error');
			campo.addClass('has-success');
			return true;
		} else {
			campo.removeClass('has-success');
			campo.addClass('has-error');
			return false;
		}
	}
	
	$scope.verificarLogin = function(usuario) {
		if (usuario) {
			$scope.usuarioLogado = true;
		}
	}

	$scope.preencheObjeto = function(cliente) {
		if(cliente)
		$scope.cliente = cliente;
	}
	
	$scope.setSala = function(numeroSala, idSala) {
		$scope.numeroSala = numeroSala;
		$scope.idSala = idSala;
	}
} ]);
