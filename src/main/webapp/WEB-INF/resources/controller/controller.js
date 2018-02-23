var app = angular.module('app', []);
app.controller('appCtrl', [ '$scope', '$http', '$timeout',function($scope, $http, $timeout) {
	$scope.numeroSala = 1;
	$scope.idSala = 1;
	$scope.listaHorario = [];
	$scope.unidades = [];
	$scope.dataSelecionada;
	$scope.agendamento;
	$scope.statusAgendamento;
	$scope.cliente;
//	$scope.usuarioLogado = true;
	$scope.listaErros = [];
	
	
	$scope.cadastrarCliente = function(cliente) {
		
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
	}
	
	$scope.verificaErros = function(erros){
		if(erros){
			$scope.listaErros = erros;
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	$scope.realizarAgendamento = function(idHora, idCliente) {

		loader = angular.element( document.querySelector('#loader'));
		sucess = angular.element( document.querySelector('#sucess'));
		
        loader.addClass('loader-ativo');
		
		data = $scope.dataSelecionada;
		agendamento = {"horario" : {"id" : idHora}, 
						"dataAgendamentoString" : data, 
						"cliente" : {"id" : idCliente},
						"sala" : {"id" : $scope.idSala},
						"status" : 1};
		$http({
			method : 'post',
			url : '/boasalasdeatendimento/realizaragendamento',
			data : JSON.stringify(agendamento),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			$scope.statusAgendamento = retorno.data;
			
			$scope.carregarHorarios($scope.numeroSala, $scope.idSala);
			
	        $timeout( function(){
	        	loader.removeClass('loader-ativo');
	        }, 1000 );
		});
	}

	$scope.carregarSalas = function() {
		
		json = { "id" : "id"};
		
		$http({
			method : 'post',
			url : '/boasalasdeatendimento/carregarsalas',
			data : JSON.stringify(json),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			$scope.unidades = retorno.data;
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
			url : '/boasalasdeatendimento/carregarhorariodisponivel',
			data : JSON.stringify(consultaSala),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			$scope.listaHorario = retorno.data;
		});
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
	
	$scope.setSala = function(numeroSala, idSala) {
		$scope.numeroSala = numeroSala;
		$scope.idSala = idSala;
	}
} ]);
