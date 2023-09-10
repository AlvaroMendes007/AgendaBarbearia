package br.com.agenda.barbearia.exception;

public class EmailDuplicadoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String MENSAGEM = "E-mail duplicado! Favor informar um e-mail válido";

    public EmailDuplicadoException() {
        super(MENSAGEM);
    }

    public EmailDuplicadoException(Throwable cause) {
        super(MENSAGEM, cause);
    }

}
