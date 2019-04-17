package br.edu.utfpr.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public class PaisDTO {
    @Getter int id;
    private String nome;
    private String sigla;
    private int codigoTelefone;
    
}