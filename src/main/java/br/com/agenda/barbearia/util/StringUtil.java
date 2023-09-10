package br.com.agenda.barbearia.util;

import org.springframework.stereotype.Component;

import br.com.agenda.barbearia.enums.TipoUsuarioEnum;

@Component
public class StringUtil {
	public boolean isEmpty(String string) {
		return string == null || string.trim().isEmpty();
	}
	
	public boolean isEmpty(TipoUsuarioEnum tipoUsuarioEnum) {
		return tipoUsuarioEnum == null || tipoUsuarioEnum.getValue().trim().isEmpty();
	}
}
