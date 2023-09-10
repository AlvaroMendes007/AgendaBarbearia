package br.com.agenda.barbearia.exception;

public class LoginInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String MENSAGEM = "Login incorreto ou inválido!";

	public LoginInvalidoException() {
		super(MENSAGEM);
	}

	public LoginInvalidoException(Throwable cause) {
		super(MENSAGEM, cause);
	}

}
