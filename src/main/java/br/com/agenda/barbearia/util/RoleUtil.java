package br.com.agenda.barbearia.util;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RoleUtil {

	public boolean possuiAdminRole() {
		return possuiRole("ROLE_ADMIN");
	}

	public boolean possuiAdminBarbeariaRole() {
		return possuiRole("ROLE_ADMIN_BARBEARIA");
	}

	private boolean possuiRole(String role) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> new SimpleGrantedAuthority(role).equals(authority));
	}
}
