package br.com.agenda.barbearia.exception;

public class UsuarioExistenteException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private static final String MENSAGEM = "Usuário já existe!";

	public UsuarioExistenteException() {
        super(MENSAGEM);
    }

	public UsuarioExistenteException(Throwable cause) {
        super(MENSAGEM, cause);
    }
}
