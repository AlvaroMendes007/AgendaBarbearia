package br.com.agenda.barbearia.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.model.Autenticacao;
import br.com.agenda.barbearia.model.Resposta;

@Service
public class RespostaService {
	
	public ResponseEntity<Resposta<Autenticacao>> criarRespostaBadRequest(String mensagem) {
        Resposta<Autenticacao> resposta = new Resposta<>();
        resposta.setCodigo(HttpStatus.BAD_REQUEST.value());
        resposta.setMensagem(mensagem);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }
	
	public ResponseEntity<Resposta<Autenticacao>> criarRespostaNotFound(String mensagem) {
        Resposta<Autenticacao> resposta = new Resposta<>();
        resposta.setCodigo(HttpStatus.NOT_FOUND.value());
        resposta.setMensagem(mensagem);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }
	
	public ResponseEntity<Resposta<Autenticacao>> criarRespostaInternalServerError(String mensagem) {
		Resposta<Autenticacao> resposta = new Resposta<>();
        resposta.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resposta.setMensagem(mensagem);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

	public ResponseEntity<?> criarRespostaSucessoRemocao(String mensagem) {
		Resposta<Autenticacao> resposta = new Resposta<>();
        resposta.setCodigo(HttpStatus.OK.value());
        resposta.setMensagem(mensagem);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
	}
	
	public ResponseEntity<?> criarRespostaForbidden(String mensagem) {
		Resposta<Autenticacao> resposta = new Resposta<>();
        resposta.setCodigo(HttpStatus.FORBIDDEN.value());
        resposta.setMensagem(mensagem);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resposta);
	}
}
