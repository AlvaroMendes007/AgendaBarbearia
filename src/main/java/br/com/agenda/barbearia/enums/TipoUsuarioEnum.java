package br.com.agenda.barbearia.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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
    
    public static TipoUsuarioEnum fromValue(String value) {
        for (TipoUsuarioEnum tipo : values()) {
            if (tipo.value.equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        return null;
    }
}
