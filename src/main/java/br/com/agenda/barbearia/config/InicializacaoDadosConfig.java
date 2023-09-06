package br.com.agenda.barbearia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.agenda.barbearia.model.TipoUsuario;
import br.com.agenda.barbearia.repository.TipoUsuarioRepository;

@Component
public class InicializacaoDadosConfig implements CommandLineRunner {
	@Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!tipoUsuarioRepository.existeTipo("cliente")) {
            TipoUsuario cliente = new TipoUsuario();
            cliente.setTipo("cliente");
            tipoUsuarioRepository.save(cliente);
        }

        if (!tipoUsuarioRepository.existeTipo("adm")) {
            TipoUsuario adm = new TipoUsuario();
            adm.setTipo("adm");
            tipoUsuarioRepository.save(adm);
        }

        if (!tipoUsuarioRepository.existeTipo("barbeiro")) {
            TipoUsuario barbeiro = new TipoUsuario();
            barbeiro.setTipo("barbeiro");
            tipoUsuarioRepository.save(barbeiro);
        }

        if (!tipoUsuarioRepository.existeTipo("barbearia")) {
            TipoUsuario barbearia = new TipoUsuario();
            barbearia.setTipo("barbearia");
            tipoUsuarioRepository.save(barbearia);
        }
    }
}
