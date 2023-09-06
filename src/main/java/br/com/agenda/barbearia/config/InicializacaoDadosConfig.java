package br.com.agenda.barbearia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
import br.com.agenda.barbearia.model.TipoUsuario;
import br.com.agenda.barbearia.repository.TipoUsuarioRepository;

@Component
public class InicializacaoDadosConfig implements CommandLineRunner {
	@Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    public void run(String... args) throws Exception {
    	 for (TipoUsuarioEnum tipo : TipoUsuarioEnum.values()) {
             if (!tipoUsuarioRepository.existeTipo(tipo.getValue())) {
                 TipoUsuario tipoUsuario = new TipoUsuario();
                 tipoUsuario.setTipo(tipo.getValue());
                 tipoUsuarioRepository.save(tipoUsuario);
             }
         }
    }
}
