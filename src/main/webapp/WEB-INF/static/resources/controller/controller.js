var app = angular.module('app', []);
app.controller('appCtrl', [ '$scope', '$http', '$timeout',function($scope, $http, $timeout) {
	$scope.numeroSala = '';
	$scope.idSala = 1;
	$scope.listaHorario = [];
	$scope.unidades = [];
	$scope.meusAgendamentos = [];
	$scope.dataSelecionada;
	$scope.agendamento;
	$scope.statusAgendamento;
	$scope.cliente = {};
	$scope.listaErros = [];
	$scope.emailValido = true;
	$scope.autenticacao = {};
	$scope.descricaoSala = '';
	
	
	//------------------------------------------------------------- Requisições ---------------------------------------------------
	$scope.cadastrarCliente = function(cliente, confirmaSenha) {
				
		campoSenha = angular.element( document.querySelector('#divConfirmarSenha'));
		
		$scope.result = angular.equals(cliente.autenticacao.senha, cliente.confirmaSenha);
		
		if ($scope.result) {
		
			campoSenha.removeClass('has-error');
			campoSenha.addClass('has-success');
			
			$scope.result = false;
				
			loader = angular.element(document.querySelector('#loader'));
			loader.addClass('loader-ativo');
			
			$scope.erro = false;
			$scope.carregando = 'Aguarde...'
		//	cliente.data = document.getElementById('data').value;
			
			$http({
				method : 'post',
				url : './cadastrarcliente',
				data : JSON.stringify(cliente),
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Content-Type", "application/json");
				},
			}).then(function(retorno) {
				
				$scope.unidades = retorno.statusText;
				$scope.sucesso = true;
				
				$scope.carregando = '';
				loader.removeClass('loader-ativo');
			}, function(erro){
				
				var tipoErro = erro.status;
				$scope.listaErros = erro.data;
				$scope.erro = true;
				
				if(tipoErro == 502){
					$scope.msgerro = 'Por favor preencher o campo: '
				} else if(tipoErro == 400){		
					$scope.msgerro = 'OPS!!: '
				} else{
					$scope.msgerro = ''	
				}		
				
				loader.removeClass('loader-ativo');
				$scope.carregando = ''
			});
		
		} else {
			campoSenha.removeClass('has-success');
			campoSenha.addClass('has-error');
			$scope.confirmaSenha = '';
			$scope.result = true;
		}
		
	}

	$scope.realizarAgendamento = function(idHora, idCliente, descricao) {

		loader = angular.element( document.querySelector('#loader'));
		sucess = angular.element( document.querySelector('#sucess'));
		btnAgendamento = angular.element( document.querySelector('#btn-'+idHora));
		btnAgendamento.addClass('btn btn-success btn-xs disabled');
		
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
	        
	        alert("Agendamento realizado");
		}, function(erro) {
			alert("Ops! Ocorreu um erro, tente novamente/Horario indisponivel");
			loader.removeClass('loader-ativo');
			btnAgendamento.addClass('btn btn-success btn-xs');
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
		}, function(erro) {
			alert("Ops! Ocorreu um erro, atualize a pagina");
		});
	}
	
	$scope.carregarHorarios = function(numeroSala, idSala, descricao) {
		
		$scope.listaHorario = [];
		
        
        $scope.descricaoSala = descricao;
		
		$scope.numeroSala = numeroSala;
		$scope.dataSelecionada = document.getElementById('data').value;
		$scope.idSala = idSala;
		//newDate = new Date(data);
		consultaSala = { "sala" : $scope.idSala, "data" : $scope.dataSelecionada};
		
		$scope.mensagemHorarios = 'Carregando...';
		
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
			$scope.mensagemHorarios = '';
		}, function(erro) {
			alert("Ops! Ocorreu um erro, tente novamente");
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
		}, function(erro) {
			alert("Ops! Ocorreu um erro, tente novamente");
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
			
        	$scope.meusAgendamentosById(idCliente);
        	loader.removeClass('loader-ativo');
        	alert("Agendamento cancelado");
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
	
	$scope.editarSenha = function(idAutenticacao) {
		
		campoSenha = angular.element( document.querySelector('#divConfirmarSenha'));
		
		$scope.autenticacao.id = idAutenticacao;
		
		$scope.result = angular.equals($scope.autenticacao.novaSenha, $scope.autenticacao.confirmaNovaSenha);
		
		loader = angular.element( document.querySelector('#loader'));
		loader.addClass('loader-ativo');
		
		if($scope.result){
			
			$scope.result = false;
			$scope.erro = false;
			$scope.sucesso = false;
			
			$http({
				method : 'post',
				url : './updatesenha',
				data : JSON.stringify($scope.autenticacao),
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Content-Type", "application/json");
				},
			}).then(function(retorno) {
				
				$scope.result = false;
				
				$scope.autenticacao = {};
				
				var retornoReq = retorno.statusText;
				$scope.sucesso = true;
				
				$scope.msgerro = 'SENHA ATUALIZADA';
				
				campoSenha.removeClass('has-error');
				campoSenha.addClass('has-success');
				loader.removeClass('loader-ativo');
				
			}, function(erro){
				
				var tipoErro = erro.status;
				$scope.listaErros = erro.data;
				$scope.erro = true;
				
				if(tipoErro == 401){
					$scope.msgerro = 'SENHA ATUAL INVALIDA'
				} else if(tipoErro == 406){		
					$scope.msgerro = 'OPS!! Ocorreu um erro, tente novamente '
				} else{
					$scope.msgerro = 'OPS!!: Ocorreu um erro inesperado'	
				}		
				
				loader.removeClass('loader-ativo');
			});
		} else {
			campoSenha.removeClass('has-success');
			campoSenha.addClass('has-error');
			$scope.confirmaSenha = '';
			$scope.result = true;
			loader.removeClass('loader-ativo');
		}
	}
	
	$scope.editarCliente = function() {
		
		loader = angular.element( document.querySelector('#loader'));
		loader.addClass('loader-ativo');
		
		$scope.result = false;
		$scope.erro = false;
		$scope.sucesso = false;
		
		$http({
			method : 'post',
			url : './updatecadastro',
			data : JSON.stringify($scope.cliente),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			
			$scope.result = false;
			
			$scope.autenticacao = {};
			
			var retornoReq = retorno.statusText;
			$scope.sucesso = true;
			
			$scope.msgerro = 'DADOS ATUALIZADOS';
			
			loader.removeClass('loader-ativo');
			
		}, function(erro){
			
			var tipoErro = erro.status;
			$scope.listaErros = erro.data;
			$scope.erro = true;
			
			if(tipoErro == 401){
				$scope.msgerro = 'NAO E POSSIVEL ALTERAR O CPF'
			} else if(tipoErro == 406){		
				$scope.msgerro = 'OPS!! Ocorreu um erro, tente novamente '
			} else{
				$scope.msgerro = 'OPS!!: Ocorreu um erro inesperado'	
			}		
			
			loader.removeClass('loader-ativo');
		});
	}
	
	
	$scope.resetSenha = function(cpf) {
		
		if(cpf != undefined){
		
			loader = angular.element( document.querySelector('#loader'));
			loader.addClass('loader-ativo');
			
			$scope.erro = false;
			$scope.sucesso = false;
			
			$http({
				method : 'post',
				url : './resetsenha/' + cpf ,
				data : JSON.stringify($scope.autenticacao),
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Content-Type", "application/json");
				},
			}).then(function(retorno) {
				
				$scope.cliente = retorno.data;
				
				var retornoReq = retorno.statusText;
				
				loader.removeClass('loader-ativo');
				
				$scope.sucesso = true;
				
				$scope.msg = 'Senha encaminhada para: ' + $scope.cliente.email;
				
			}, function(erro){
				
				var tipoErro = erro.status;
				$scope.listaErros = erro.data;
				$scope.erro = true;
				
				if(tipoErro == 401){
					$scope.msg = 'CPF inexistente em nossa base'
				} 
				loader.removeClass('loader-ativo');
			});
		
		} else {
			$scope.erro = true;
			$scope.msg = 'Campo CPF obrigatorio'
		}
	}
	
	
	$scope.getCliente = function(idCliente) {
		
		loader = angular.element( document.querySelector('#loader'));
		loader.addClass('loader-ativo');
		
		$scope.erro = false;
		$scope.sucesso = false;
		
		$http({
			method : 'post',
			url : './getcliente/' + idCliente ,
			data : JSON.stringify($scope.autenticacao),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
		}).then(function(retorno) {
			
			$scope.cliente = retorno.data;
			
			var retornoReq = retorno.statusText;
			
			loader.removeClass('loader-ativo');
			
		}, function(erro){
			
			var tipoErro = erro.status;
			$scope.listaErros = erro.data;
			$scope.erro = true;
			
			if(tipoErro == 401){
				$scope.msgerro = 'SENHA ATUAL INVALIDA'
			} 
			loader.removeClass('loader-ativo');
		});
	}
	
	//-----------------------------------------------------FIM REQUISIÇÕES-----------------------------------------------------------

	
	
	//----------------------------------------------------------------------------------------------------------------
	$scope.validaSenha = function(senha, senha2){
		campoSenha = angular.element( document.querySelector('#divConfirmarSenha'));
		
		$scope.result = angular.equals(senha, senha2);
		
		if (!$scope.result) {
			
			campoSenha.removeClass('has-success');
			campoSenha.addClass('has-error');
			$scope.confirmaSenha = '';
			
			$scope.result = true;
		} else {
			
			campoSenha.removeClass('has-error');
			campoSenha.addClass('has-success');
			
			$scope.result = false;
		}
		
	}
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
