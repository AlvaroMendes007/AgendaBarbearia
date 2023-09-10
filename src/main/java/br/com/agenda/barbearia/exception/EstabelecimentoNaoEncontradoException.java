package br.com.agenda.barbearia.exception;

public class EstabelecimentoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private static final String MENSAGEM = "Estabelecimento não encontrado";

    public EstabelecimentoNaoEncontradoException() {
        super(MENSAGEM);
    }

    public EstabelecimentoNaoEncontradoException(Throwable cause) {
        super(MENSAGEM, cause);
    }
}
