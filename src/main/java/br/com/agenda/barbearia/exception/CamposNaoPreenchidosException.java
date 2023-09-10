package br.com.agenda.barbearia.exception;

public class CamposNaoPreenchidosException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String MENSAGEM = "Os campos obrigatórios não estão preenchidos!";

    public CamposNaoPreenchidosException() {
        super(MENSAGEM);
    }

    public CamposNaoPreenchidosException(Throwable cause) {
        super(MENSAGEM, cause);
    }

}
