package br.com.boasalasdeatendimento.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.model.Email;

public class CommonsMail {
	public CommonsMail() throws EmailException, MalformedURLException {
		// enviaEmailSimples();

		Email sendEmail = new Email();

		sendEmail.setHostName("smtp.gmail.com");
		sendEmail.setEmailDestinatario("cesar.quadros88@gmail.com");
		sendEmail.setNomeDestinatatio("Cesar");

		sendEmail.setEmailRemetente("cleu@vcrh.com.br");
		sendEmail.setNomeRemetente("Salas de atendimento");

		sendEmail.setAssuntoEmail("Recuperação de senha");

		sendEmail.setMensagemHtml("<h3>TITULO</h3> <br /> Sua Senha é:");

		sendEmail.setMensagemEmail("Email de recuperação de senha: Sua senha é 123456");
		sendEmail.setAutenticacaoEmail("ninoreggae@gmail.com");
		sendEmail.setAutenticacaoSenha("12345");
		sendEmail.setPortaSmtp(587);

		enviaEmailFormatoHtml(sendEmail);
		// enviaEmailComAnexo();
	}

	/**
	 * envia email simples(somente texto)
	 * 
	 * @throws EmailException
	 */
	public void enviaEmailSimples() {
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		try {
			email.addTo("cesar.quadros88@gmail.com", "Guilherme");// destinatário
			email.setFrom("ninoreggae@gmail.com", "Eu"); // remetente
			email.setSubject("DIEGOOOOOOOOOOOOO simples"); // assunto do e-mail
			email.setMsg("Teste de Email utilizando commons-email"); // conteudo do e-mail
			email.setAuthentication("ninoreggae@gmail.com", "1234");
			email.setSmtpPort(587);
			email.setSSL(true);
			email.setTLS(true);
			email.send();
			System.out.println("email enviado");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Envia email no formato HTML
	 * 
	 * @throws EmailException
	 * @throws MalformedURLException
	 */
	public static Boolean enviaEmailFormatoHtml(Email objEmail) {

		HtmlEmail email = new HtmlEmail();

		try {
			email.setHtmlMsg(objEmail.getMensagemHtml());
			// configure uma mensagem alternativa caso o servidor não suporte HTML
			email.setTextMsg(objEmail.getMensagemHtml());
			
			email.setHostName(objEmail.getHostName()); // o servidor SMTP para envio do e-mail
			email.addTo(objEmail.getEmailDestinatario(), objEmail.getNomeDestinatatio()); // destinatário
			email.setFrom(objEmail.getEmailRemetente(), objEmail.getNomeRemetente()); // remetente
			email.setSubject(objEmail.getAssuntoEmail()); // assunto do e-mail
			// email.setMsg(objEmail.getMensagemEmail()); //conteudo do e-mail
			email.setAuthentication(objEmail.getAutenticacaoEmail(), objEmail.getAutenticacaoSenha());
			email.setSmtpPort(objEmail.getPortaSmtp());
			email.setSSL(true);
			email.setTLS(true);
			// envia email
			email.send();
			System.out.println("email enviado");
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void enviaEmailComAnexo() {
		// cria o anexo 1.
		EmailAttachment anexo1 = new EmailAttachment();
		anexo1.setPath("teste/teste.txt"); // caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
		anexo1.setDisposition(EmailAttachment.ATTACHMENT);
		anexo1.setDescription("Exemplo de arquivo anexo");
		anexo1.setName("teste.txt");
		// cria o anexo 2.
		EmailAttachment anexo2 = new EmailAttachment();
		anexo2.setPath("teste/teste2.jsp"); // caminho do arquivo (RAIZ_PROJETO/teste/teste2.jsp)
		anexo2.setDisposition(EmailAttachment.ATTACHMENT);
		anexo2.setDescription("Exemplo de arquivo anexo");
		anexo2.setName("teste2.jsp");
		// configura o email
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		try {
			email.addTo("teste@gmail.com", "Guilherme"); // destinatário
			email.setFrom("teste@gmail.com", "Eu"); // remetente
			email.setSubject("Teste -&gt; Email com anexos"); // assunto do e-mail
			email.setMsg("Teste de Email utilizando commons-email"); // conteudo do e-mail
			email.setAuthentication("teste", "xxxxx");
			email.setSmtpPort(465);
			email.setSSL(true);
			email.setTLS(true);
			// adiciona arquivo(s) anexo(s)
			email.attach(anexo1);
			email.attach(anexo2);
			// envia o email
			email.send();
			System.out.println("email enviado");
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public static Email criarEmail(Cliente cliente) {
		
		Email email = new Email();

		email.setHostName("smtp.gmail.com");
		email.setEmailDestinatario(cliente.getEmail());
		email.setNomeDestinatatio(cliente.getNome());

		email.setEmailRemetente("cleu@vcrh.com.br");
		email.setNomeRemetente("Salas de atendimento");

		email.setAssuntoEmail("Recuperação de senha");

		email.setMensagemHtml(gerarMensagemEmail(cliente));

		email.setMensagemEmail("Email de recuperação de senha: Sua senha é" + cliente.getAutenticacao().getNovaSenha());
		email.setAutenticacaoEmail("cesarquadros.developer.test");
		email.setAutenticacaoSenha("Developer@88");
		email.setPortaSmtp(587);
		
		return email;
	}
	
	public static String gerarMensagemEmail(Cliente cliente) {
		
		StringBuilder mensagemEmail = new StringBuilder();
		
		mensagemEmail.append("Salas de atendimento \n");
		mensagemEmail.append("Conforme solicitado, segue sua nova senha temporaria: \n");
		
		mensagemEmail.append("Login: \n");
		mensagemEmail.append(cliente.getAutenticacao().getUsuario());
		mensagemEmail.append("\n");
		
		mensagemEmail.append("Senha temporaria: \n");
		mensagemEmail.append(cliente.getAutenticacao().getNovaSenha());
		mensagemEmail.append("\n");
		
		mensagemEmail.append("Para alterar a senha clique no menu, no canto superior direito, vá em  'MEU PERFIL' \n");
		
		mensagemEmail.append("Caso não tenha solicitado a recuperação de senha, entre em contato cleu@vcrh.com.br \n");
		
		return mensagemEmail.toString();
	}
}
