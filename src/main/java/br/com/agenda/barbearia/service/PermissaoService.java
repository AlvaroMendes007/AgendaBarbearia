package br.com.agenda.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.exception.SemPermissaoExecutarAcaoException;
import br.com.agenda.barbearia.util.RoleUtil;

@Service
public class PermissaoService {

    @Autowired
    private RoleUtil roleUtil;

    public void verificarPermissaoAdminOuAdminBarbearia() {
        boolean usuarioTemTipoAdminBarbearia = roleUtil.possuiAdminBarbeariaRole();
        boolean usuarioTemTipoAdmin = roleUtil.possuiAdminRole();

        if (!usuarioTemTipoAdmin && !usuarioTemTipoAdminBarbearia) {
            throw new SemPermissaoExecutarAcaoException();
        }
    }
}
