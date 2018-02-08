var app = angular.module('app', []);
app.controller('appCtrl', [ '$scope', '$http', function($scope, $http) {
	$scope.numeroSala = 1;
	$scope.listaHorario = [];
	$scope.unidades = [];
	$scope.dataA;
	$scope.usuarioLogado = true;
	
	$scope.verificarLogin = function(usuario) {
		if (usuario) {
			$scope.usuarioLogado = true;
		}
	}
	
	$scope.setSala = function(sala) {
		$scope.numeroSala = sala;
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
		
		data = document.getElementById('data').value;
		sala = $scope.numeroSala;
		//newDate = new Date(data);
		consultaSala = { "sala" : sala, "data" : data};
		
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
