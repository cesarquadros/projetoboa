var app = angular.module('app', []);
app.controller('appCtrl', [ '$scope', '$http', function($scope, $http) {
	$scope.usuarioLogado = false;
	$scope.numeroSala = 1;
	$scope.listaHorario = [];


	$scope.setSala = function(sala) {
		$scope.numeroSala = sala;
	}

	$scope.testeBusca = function() {
		
		data = document.getElementById('data').value;
		sala = $scope.numeroSala;
		newDate =new Date(data);
		DataSala = { "numero" : + sala, "data" : + newDate};
		
		$http({
			method : 'post',
			url : '/boasalasdeatendimento/listahorarios',
			data : JSON.stringify(DataSala),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			$scope.listaHorario = retorno.data;

		});
	}
	
	$scope.teste = function(nome) {
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
