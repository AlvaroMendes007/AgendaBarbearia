package br.com.agenda.barbearia.exception;

public class CampoNaoPreenchidoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

    public CampoNaoPreenchidoException(String mensagem) {
        super(mensagem);
    }
}
