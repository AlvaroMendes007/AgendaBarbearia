package br.com.agenda.barbearia.exception;

public class SemPermissaoExcluirTipoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private static final String MENSAGEM = "Sem permissão para excluir o tipo informado!";
	private static final String MENSAGEM_ESPECIFICA = "Sem permissão para excluir o tipo informado: ";

	public SemPermissaoExcluirTipoException() {
		super(MENSAGEM);
	}
	
	public SemPermissaoExcluirTipoException(String tipo) {
		super(MENSAGEM_ESPECIFICA + tipo + "!");
	}

	public SemPermissaoExcluirTipoException(Throwable cause) {
		super(MENSAGEM, cause);
	}
}
