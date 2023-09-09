package br.com.agenda.barbearia.dto;

import java.sql.Blob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioBarbeariaDTO {
    private String nome;
    private Blob foto;
    private Long usuarioId;
    private Long estabelecimentoId;
}