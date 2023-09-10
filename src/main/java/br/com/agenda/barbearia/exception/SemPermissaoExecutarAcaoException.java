package br.com.agenda.barbearia.exception;

public class SemPermissaoExecutarAcaoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private static final String MENSAGEM = "Sem permissão para executar essa ação!";

	public SemPermissaoExecutarAcaoException() {
		super(MENSAGEM);
	}

	public SemPermissaoExecutarAcaoException(Throwable cause) {
		super(MENSAGEM, cause);
	}
}
