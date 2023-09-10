package br.com.agenda.barbearia.dto;

import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
import lombok.Data;

@Data
public class UsuarioRegisterDTO {
	private String email;	
    private String senha;
    private TipoUsuarioEnum tipoUsuario;
    private String tipoUsuarioString;
}
