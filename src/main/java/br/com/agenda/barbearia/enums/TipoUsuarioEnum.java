package br.com.agenda.barbearia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoUsuarioEnum {
	ADMIN(1, "ADMIN"), 
	CLIENTE(2, "CLIENTE"), 
	BARBEIRO(3, "BARBEIRO"), 
	ADMIN_BARBEARIA(4, "ADMIN_BARBEARIA");
	
	private final Integer key;
    private final String value;
}
