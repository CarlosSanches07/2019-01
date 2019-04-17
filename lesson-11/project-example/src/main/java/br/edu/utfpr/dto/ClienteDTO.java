package br.edu.utfpr.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import br.edu.utfpr.excecao.NomeClienteMenor5CaracteresException;

@Data
@Builder

public class ClienteDTO {

    @Getter int id;
    @Getter String nome;
    @Getter private int idade;
    @Getter private String telefone;
    @Getter private double limiteCredito;
    @Getter private PaisDTO pais;

    public void setNome(String nome) throws NomeClienteMenor5CaracteresException {
        if (nome.length() < 5)
            throw new NomeClienteMenor5CaracteresException(nome);

        this.nome = nome;
    }
    
}