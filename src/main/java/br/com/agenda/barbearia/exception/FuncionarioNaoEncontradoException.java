package br.com.agenda.barbearia.exception;

public class FuncionarioNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private static final String MENSAGEM = "Funcionário não encontrado";

    public FuncionarioNaoEncontradoException() {
        super(MENSAGEM);
    }

    public FuncionarioNaoEncontradoException(Throwable cause) {
        super(MENSAGEM, cause);
    }
}
