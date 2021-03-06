var app = angular.module('app', []);
app.controller('appCtrl', [ '$scope', '$http', '$timeout',function($scope, $http, $timeout) {

	$scope.agendamentos = [];
	$scope.relatorioAgendamento;
	$scope.relatorioClientes = [];
	$scope.unidades = [];
	
	//------------------------------------------------------------- Requisições ---------------------------------------------------

	$scope.gerarRelatorio = function() {
		
		loader = angular.element( document.querySelector('#loader'));
		loader.addClass('loader-ativo');
		
		status = document.getElementById('status').value;
		dataInicio = document.getElementById('dataInicio').value;
		dataFim = document.getElementById('dataFim').value;
		
		$scope.relatorioAgendamento = {"dataInicio" : dataInicio,
										"dataFim": dataFim,
										"status": status};
		
		json = $scope.relatorioAgendamento;
		
		$http({
			method : 'post',
			url : './relatorioagendamentosadm',
			data : JSON.stringify(json),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			$scope.agendamentos = retorno.data;
			loader.removeClass('loader-ativo');
		}, function(erro){
			alert("Ops! Ocorreu um erro, tente novamente");
			loader.removeClass('loader-ativo');
		});
	}
	
	$scope.downloadCsv = function() {
		
		loader = angular.element( document.querySelector('#loader'));
		loader.addClass('loader-ativo');
		
		status = document.getElementById('status').value;
		dataInicio = document.getElementById('dataInicio').value;
		dataFim = document.getElementById('dataFim').value;
		
		$scope.relatorioAgendamento = {"dataInicio" : dataInicio,
										"dataFim": dataFim,
										"status": status};
		
		json = $scope.relatorioAgendamento;
		
		$http({
			method : 'post',
			url : './downloadcsv',
			data : JSON.stringify($scope.agendamentos),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			
			 var headers = data.headers();
			 	
			 	var filename = headers['x-filename'];
		        var contentType = headers['content-type'];
		 
		        var linkElement = document.createElement('a');
		        try {
		            var blob = new Blob([data], { type: contentType });
		            var url = window.URL.createObjectURL(blob);
		 
		            linkElement.setAttribute('href', url);
		            linkElement.setAttribute("download", filename);
		 
		            var clickEvent = new MouseEvent("click", {
		                "view": window,
		                "bubbles": true,
		                "cancelable": false
		            });
		            linkElement.dispatchEvent(clickEvent);
		        } catch (ex) {
		            console.log(ex);
		        }
			$scope.agendamentos = retorno.data;
			loader.removeClass('loader-ativo');
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
	        	$scope.gerarRelatorio();
	        	alert("Agendamento cancelado");
	        	loader.removeClass('loader-ativo');
				return true;
		}, function(erro){
			
			var tipoErro = erro.status;
			
			if(tipoErro == 401){
				alert("Horario limite para cancelamento excedido");
			} else {
				alert("OPS!!: Ocorreu um erro inesperado");
			}
			loader.removeClass('loader-ativo');
		});
	}
	
	$scope.finalizarAgendamento = function(idAgendamento, idCliente) {
		
		json = { "id" : "id"};
		loader = angular.element( document.querySelector('#loader'));
		loader.addClass('loader-ativo');
		
		$http({
			method : 'post',
			url : './finalizaragendamento/'+ idAgendamento,
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
		        	$scope.gerarRelatorio();
				
				return true;
			} else {
				return false;
			}
		}, function(erro){
			alert("Ops! Ocorreu um erro, tente novamente");
			loader.removeClass('loader-ativo');
		});
	}
	
	$scope.getRelatorioClientes = function(idAgendamento, idCliente) {
		
		json = { "id" : "id"};
		loader = angular.element( document.querySelector('#loader-cliente'));
		loader.addClass('loader-ativo');
		
		$http({
			method : 'post',
			url : './relatorioclientes',
			data : JSON.stringify(json),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			$scope.relatorioClientes  =  retorno.data;
			
			if(retorno == "OK"){
				
				loader.removeClass('loader-ativo');				
				return true;
			} else {
				loader.removeClass('loader-ativo');	
				return false;
			}
		});
	}
	
	$scope.carregarUnidades = function() {
		
		loader = angular.element( document.querySelector('#loader-unidades'));
		loader.addClass('loader-ativo');
		
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
			loader.removeClass('loader-ativo');
		}, function(erro) {
			alert("Ops! Ocorreu um erro, atualize a pagina");
			loader.removeClass('loader-ativo');
		});
	}
	//-----------------------------------------------------FIM REQUISIÇÕES-----------------------------------------------------------

} ]);
