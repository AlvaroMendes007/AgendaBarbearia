package br.com.agenda.barbearia.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
import lombok.Data;

@Data
public class UsuarioRegisterDTO {
	@NotEmpty
	private String email;
	
	@NotEmpty
    private String senha;
	
	@NotNull
    private TipoUsuarioEnum tipoUsuario;
}
