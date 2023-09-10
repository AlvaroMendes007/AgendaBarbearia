package br.com.agenda.barbearia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resposta<T> {
	private int codigo;
    private String mensagem;
}
