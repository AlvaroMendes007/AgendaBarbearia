package br.com.agenda.barbearia.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String MENSAGEM = "Usuário não encontrado";

    public UsuarioNaoEncontradoException() {
        super(MENSAGEM);
    }

    public UsuarioNaoEncontradoException(Throwable cause) {
        super(MENSAGEM, cause);
    }
}
