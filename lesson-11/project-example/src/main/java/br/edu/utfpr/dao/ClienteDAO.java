package br.edu.utfpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.edu.utfpr.dto.ClienteDTO;
import br.edu.utfpr.dto.PaisDTO;
import lombok.extern.java.Log;

@Log
public class ClienteDAO {

    // Responsável por criar a tabela Cliente no banco.
    public ClienteDAO() {

        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {

            log.info("Criando tabela cliente ...");
            conn.createStatement().executeUpdate(
            "CREATE TABLE cliente (" +
						"id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT id_cliente_pk PRIMARY KEY," +
						"nome varchar(255)," +
						"telefone varchar(30)," + 
						"idade int," + 
                        "limiteCredito double," +
                        "id_pais int)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Responsável por inserir um Cliente no banco
    public void inserirCliente(ClienteDTO cliente) {
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {
            
            System.out.println("Inserindo cliente");

            String sql = "insert into cliente(" + "nome,"
                                                + "telefone,"
                                                + "idade,"
                                                + "limiteCredito,"
                                                + "id_pais) "
                        + "values( ?, ?, ?, ?, ?)";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getTelefone());
            statement.setInt(3, cliente.getIdade());
            statement.setDouble(4, cliente.getLimiteCredito());
            statement.setInt(5, cliente.getPaisDTO().getId());

            if(statement.executeUpdate() <= 0)
                throw new Exception();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Responsável pela leitura de um Cliente do banco
    public ClienteDTO lerCliente(int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {
            
            System.out.println("Lendo cliente");
            
            String sql = "select " + "nome,"
                                   + "telefone,"
                                   + "limiteCredito,"
                                   + "id_pais "
                        +"from cliente "
                        +"where id = ?";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            ClienteDTO cliente;

            ResultSet result = statement.executeQuery();
            while(result.next()){
                cliente = ClienteDTO.builder().id(id)
                                    .nome(result.getString(1))
                                    .telefone(result.getString(2))
                                    .limiteCredito(result.getBoolean(3))
                                    .pais(PaisDTO.builder().id(result.getInt(4)).build())
                                    .build();
            }

            if(cliente == null)
                throw new Exception();
            
            return cliente;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Responsável pela listagem de Clientes do banco
    public ArrayList<ClienteDTO> listarCliente() {
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {
            
            System.out.println("Listando clientes");
            
            String sql = "select " + "id,"
                                   + "nome,"
                                   + "telefone,"
                                   + "limiteCredito,"
                                   + "id_pais "
                        +"from cliente";
            
            PreparedStatement statement = conn.prepareStatement(sql);

            ArrayList<ClienteDTO> clientes;

            ResultSet result = statement.executeQuery();
            while(result.next()){
                clientes.add(ClienteDTO.builder().id(1)
                                    .nome(result.getString(2))
                                    .telefone(result.getString(3))
                                    .limiteCredito(result.getBoolean(4))
                                    .pais(PaisDTO.builder().id(result.getInt(5)).build())
                                    .build());
            }

            if(clientes.isEmpty())
                throw new Exception();
            
            return clientes;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Responsável pela edição de um Cliente no banco
    public void editarCliente(ClienteDTO cliente){
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {
            
            System.out.println("Editando cliente");

            String sql = "update cliente set " + "nome = ?,"
                                               + "telefone = ?,"
                                               + "idade = ?,"
                                               + "limiteCredito = ?,"
                                               + "id_pais = ? "
                        + "where id = ?";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getTelefone());
            statement.setInt(3, cliente.getIdade());
            statement.setDouble(4, cliente.getLimiteCredito());
            statement.setInt(5, cliente.getPaisDTO().getId());
            statement.setInt(6, cliente.getId());

            if(statement.executeUpdate() <= 0)
                throw new Exception();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Responsável pela exclusão de um Cliente do banco
    public void excluirCliente(int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {
            
            System.out.println("Excluindo cliente");

            String sql = "delete from cliente where id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            if(statement.executeUpdate() <= 0)
                throw new Exception();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}