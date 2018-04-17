package br.com.boasalasdeatendimento.model;

public class Email {
	
	private String hostName;
	private String emailDestinatario;
	private String nomeDestinatatio;
	private String emailRemetente;
	private String nomeRemetente;
	private String assuntoEmail;
	
	private String mensagemEmail;
	private String mensagemHtml;
	
	private String autenticacaoEmail;
	private String autenticacaoSenha;
	private Integer portaSmtp;
	
	
	public String getMensagemHtml() {
		return mensagemHtml;
	}
	public void setMensagemHtml(String mensagemHtml) {
		this.mensagemHtml = mensagemHtml;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getEmailDestinatario() {
		return emailDestinatario;
	}
	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}
	public String getNomeDestinatatio() {
		return nomeDestinatatio;
	}
	public void setNomeDestinatatio(String nomeDestinatatio) {
		this.nomeDestinatatio = nomeDestinatatio;
	}
	public String getEmailRemetente() {
		return emailRemetente;
	}
	public void setEmailRemetente(String emailRemetente) {
		this.emailRemetente = emailRemetente;
	}
	public String getNomeRemetente() {
		return nomeRemetente;
	}
	public void setNomeRemetente(String nomeRemetente) {
		this.nomeRemetente = nomeRemetente;
	}
	public String getAssuntoEmail() {
		return assuntoEmail;
	}
	public void setAssuntoEmail(String assuntoEmail) {
		this.assuntoEmail = assuntoEmail;
	}
	public String getMensagemEmail() {
		return mensagemEmail;
	}
	public void setMensagemEmail(String mensagemEmail) {
		this.mensagemEmail = mensagemEmail;
	}
	public String getAutenticacaoEmail() {
		return autenticacaoEmail;
	}
	public void setAutenticacaoEmail(String autenticacaoEmail) {
		this.autenticacaoEmail = autenticacaoEmail;
	}
	public String getAutenticacaoSenha() {
		return autenticacaoSenha;
	}
	public void setAutenticacaoSenha(String autenticacaoSenha) {
		this.autenticacaoSenha = autenticacaoSenha;
	}
	public Integer getPortaSmtp() {
		return portaSmtp;
	}
	public void setPortaSmtp(Integer portaSmtp) {
		this.portaSmtp = portaSmtp;
	}
}
