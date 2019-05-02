package br.edu.utfpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import br.edu.utfpr.dto.PaisDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.java.Log;
import utfpr.daopackage.AbstractDAO;

@Log
public class PaisDAO extends AbstractDAO<PaisDAO> {

    // Responsável por criar a tabela País no banco
    public PaisDAO() {
        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {

            log.info("Criando tabela pais ...");
            conn.createStatement().executeUpdate(
                    "CREATE TABLE pais ("
                    + "id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT id_pais_pk PRIMARY KEY,"
                    + "nome varchar(255),"
                    + "sigla varchar(3),"
                    + "codigoTelefone int)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement incluirStatement(Connection conn, PaisDTO pais) {

        String sql = "INSERT INTO pais (nome, sigla, codigoTelefone) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, pais.getNome());
        statement.setString(2, pais.getSigla());
        statement.setInt(3, pais.getCodigoTelefone());
        
        return statement;
    }

    public List<PaisDTO> listarTodos(Connection conn) {

        List<PaisDTO> resultado = new ArrayList<>();

        String sql = "SELECT * FROM pais";

        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);

        int count = 0;

        while (result.next()) {

            resultado.add(
                    PaisDTO.builder()
                            .codigoTelefone(result.getInt("codigoTelefone"))
                            .id(result.getInt("id"))
                            .nome(result.getString("nome"))
                            .sigla(result.getString("sigla"))
                            .build()
            );
        }

        return resultado;
    }

    public PreparedStatement excluir(Connection conn, int id) {
    
        String sql = "DELETE FROM pais WHERE id=?";
    
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
    
        return statement;
    }

    public PreparedStatement alterarStatement(Connection conn, PaisDTO pais) {

        String sql = "UPDATE pais SET nome=?, sigla=?, codigoTelefone=? WHERE id=?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, pais.getNome());
        statement.setString(2, pais.getSigla());
        statement.setInt(3, pais.getCodigoTelefone());
        statement.setInt(4, pais.getId());
        
        return statement;
    }
    
    public PaisDTO listarPorId (int id) {
        return this.listar().stream().filter(p -> p.getId() == id).findAny().orElseThrow(RuntimeException::new);
    }

}
