jQuery(function($){
   $("#telefonefixo").mask("(99)9999-9999");
   $("#telefonecelular").mask("(99)99999-9999");
   $("#cpf").mask("999.999.999-99");
   $(".cep").mask("99999-999");
   $(".estado").mask("aa");
   $(".cartao").mask("9999999999999999");
   $(".codseguranca").mask("999");
   $(".validadecartao").mask("99/9999");
   $('.money').mask('999999999,99', {reverse: true});
});