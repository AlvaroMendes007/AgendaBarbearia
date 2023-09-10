package br.com.agenda.barbearia.exception;

public class TipoUsuarioNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String MENSAGEM = "Tipo de usuário não encontrado!";

	public TipoUsuarioNaoEncontradoException() {
		super(MENSAGEM);
	}

	public TipoUsuarioNaoEncontradoException(Throwable cause) {
		super(MENSAGEM, cause);
	}
}
