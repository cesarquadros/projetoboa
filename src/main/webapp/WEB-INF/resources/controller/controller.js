var app = angular.module('app', []);
app.controller('appCtrl', [ '$scope', '$http', function($scope, $http) {
	$scope.numeroSala = 1;
	$scope.idSala = 1;
	$scope.listaHorario = [];
	$scope.unidades = [];
	$scope.dataSelecionada;
	$scope.agendamento;
//	$scope.usuarioLogado = true;
	
	$scope.verificarLogin = function(usuario) {
		if (usuario) {
			$scope.usuarioLogado = true;
		}
	}
	
	$scope.ativo = function(numero) {
		if (numero == 0 ) {
			return true;
		} else {
			return false
		}
	}
	
	$scope.setSala = function(numeroSala, idSala) {
		$scope.numeroSala = numeroSala;
		$scope.idSala = idSala;
	}
	
	$scope.realizarAgendamento = function(idHora, idCliente) {
		console.log(idHora);
		data = $scope.dataSelecionada;
		agendamento = {"horario" : {"id" : idHora }, 
						"dataAgendamentoString" : data, 
						"cliente" : {"id" : idCliente},
						"sala" : {"id" : $scope.idSala}}
		
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
	
	$scope.carregarHorarios = function() {
		
		$scope.dataSelecionada = document.getElementById('data').value;
		sala = $scope.numeroSala;
		//newDate = new Date(data);
		consultaSala = { "sala" : sala, "data" : $scope.dataSelecionada};
		
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
	
	$scope.verificaCampoVazio = function(nome) {
		campo = angular.element( document.querySelector('#divNome'));
		
		if(nome != undefined){
			campo.addClass('has-success');
			return true;
		} else {
			campo.addClass('has-error');
			return false;
		}
	}
} ]);
