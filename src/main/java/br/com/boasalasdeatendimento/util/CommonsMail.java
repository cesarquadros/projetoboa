package br.com.boasalasdeatendimento.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import br.com.boasalasdeatendimento.model.Email;

public class CommonsMail {
	public CommonsMail() throws EmailException, MalformedURLException {
		//enviaEmailSimples();
		
		Email sendEmail = new Email(); 
		
		sendEmail.setHostName("smtp.gmail.com");
		sendEmail.setEmailDestinatario("cesar.quadros88@gmail.com");
		sendEmail.setNomeDestinatatio("Cesar");

		sendEmail.setEmailRemetente("ninoreggae@gmail.com");
		sendEmail.setNomeRemetente("Salas de atendimento");
		
		sendEmail.setAssuntoEmail("Recuperação de senha");
		
		sendEmail.setMensagemHtml("<h3>TITULO</h3> <br /> Sua Senha é:");
		
		sendEmail.setMensagemEmail("Email de recuperação de senha: Sua senha é 123456");
		sendEmail.setAutenticacaoEmail("ninoreggae@gmail.com");
		sendEmail.setAutenticacaoSenha("Ces@r190788");
		sendEmail.setPortaSmtp(587);
		
		enviaEmailFormatoHtml(sendEmail);
		//enviaEmailComAnexo();
	}
	/**
	 * envia email simples(somente texto)
	 * @throws EmailException
	 */
	public void enviaEmailSimples() throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		email.addTo("cesar.quadros88@gmail.com", "Guilherme"); //destinatário
		email.setFrom("ninoreggae@gmail.com", "Eu"); // remetente
		email.setSubject("DIEGOOOOOOOOOOOOO simples"); // assunto do e-mail
		email.setMsg("Teste de Email utilizando commons-email"); //conteudo do e-mail
		email.setAuthentication("ninoreggae@gmail.com", "Ces@r190788");
		email.setSmtpPort(587);
		email.setSSL(true);
		email.setTLS(true);
		email.send();	
		System.out.println("email enviado");
	}
	/**
	 * Envia email no formato HTML
	 * @throws EmailException 
	 * @throws MalformedURLException 
	 */
	public void enviaEmailFormatoHtml(Email objEmail ) throws EmailException, MalformedURLException {
		
		HtmlEmail email = new HtmlEmail();
		
		// adiciona uma imagem ao corpo da mensagem e retorna seu id
		URL url = new URL("https://www.emporiodoeva.com.br/wp-content/uploads/2015/04/coruja-azul1-e1431120935578.jpg");
		String cid = email.embed(url, "Logo Salas de atendimento");	
		// configura a mensagem para o formato HTML
		email.setHtmlMsg(objEmail.getMensagemHtml());
		// configure uma mensagem alternativa caso o servidor não suporte HTML
		email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
		email.setHostName(objEmail.getHostName()); // o servidor SMTP para envio do e-mail
		email.addTo(objEmail.getEmailDestinatario(), objEmail.getNomeDestinatatio()); //destinatário
		email.setFrom(objEmail.getEmailRemetente(), objEmail.getNomeRemetente()); // remetente
		email.setSubject(objEmail.getAssuntoEmail()); // assunto do e-mail
		//email.setMsg(objEmail.getMensagemEmail()); //conteudo do e-mail
		email.setAuthentication(objEmail.getAutenticacaoEmail(), objEmail.getAutenticacaoSenha());
		email.setSmtpPort(objEmail.getPortaSmtp());
		email.setSSL(true);
		email.setTLS(true);
		// envia email
		email.send();
		System.out.println("email enviado");
	}

	public void enviaEmailComAnexo() throws EmailException{
		// cria o anexo 1.
		EmailAttachment anexo1 = new EmailAttachment();
		anexo1.setPath("teste/teste.txt"); //caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
		anexo1.setDisposition(EmailAttachment.ATTACHMENT);
		anexo1.setDescription("Exemplo de arquivo anexo");
		anexo1.setName("teste.txt");		
		// cria o anexo 2.
		EmailAttachment anexo2 = new EmailAttachment();
		anexo2.setPath("teste/teste2.jsp"); //caminho do arquivo (RAIZ_PROJETO/teste/teste2.jsp)
		anexo2.setDisposition(EmailAttachment.ATTACHMENT);
		anexo2.setDescription("Exemplo de arquivo anexo");
		anexo2.setName("teste2.jsp");		
		// configura o email
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		email.addTo("teste@gmail.com", "Guilherme"); //destinatário
		email.setFrom("teste@gmail.com", "Eu"); // remetente
		email.setSubject("Teste -&gt; Email com anexos"); // assunto do e-mail
		email.setMsg("Teste de Email utilizando commons-email"); //conteudo do e-mail
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
	}
	
	public static void main(String[] args) throws EmailException, MalformedURLException {
		new CommonsMail();
	}
}
