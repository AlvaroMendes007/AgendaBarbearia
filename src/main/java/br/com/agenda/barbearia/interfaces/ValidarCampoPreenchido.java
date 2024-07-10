package br.com.agenda.barbearia.interfaces;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidarCampoPreenchido {
    String message() default "Campo não preenchido";
}