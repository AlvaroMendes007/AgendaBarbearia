package br.com.agenda.barbearia.validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.agenda.barbearia.exception.CampoNaoPreenchidoException;
import br.com.agenda.barbearia.interfaces.ValidarCampoPreenchido;

public class CampoPreenchidoValidator {
	
    public static void validar(Object dto) {
    	List<String> camposNaoPreenchidos = new ArrayList<>();
        validarCampos(dto, camposNaoPreenchidos);
        if (!camposNaoPreenchidos.isEmpty()) {
            String mensagemErro = "Campo(s) " + String.join(", ", camposNaoPreenchidos) + " não preenchido(s)";
            throw new CampoNaoPreenchidoException(mensagemErro);
        }
    }

    private static void validarCampos(Object dto, List<String> camposNaoPreenchidos) {
        Class<?> clazz = dto.getClass();
        Field[] fields = clazz.getDeclaredFields();
        	
        for (Field field : fields) {
            if (field.isAnnotationPresent(ValidarCampoPreenchido.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(dto);
                    if (value == null || value.toString().trim().isEmpty()) {
                        String fieldName = field.getName();
                        camposNaoPreenchidos.add(fieldName);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                // Verifica se o campo é um objeto e faz uma validação recursiva
                if (!field.getType().isPrimitive() && !field.getType().equals(String.class)) {
                    try {
                        field.setAccessible(true);
                        Object nestedDto = field.get(dto);
                        if (nestedDto != null) {
                            validarCampos(nestedDto, camposNaoPreenchidos);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

