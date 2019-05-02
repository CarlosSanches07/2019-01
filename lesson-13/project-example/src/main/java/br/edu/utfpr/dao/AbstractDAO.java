package utfpr.daopackage

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;

br.edu.utfpr.dao;

public abstract class AbstractDAO<T> {

    public List<T> listar() {
        List<T> resultado = new ArrayList();
        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) { 
            
            resultado = listarTodos(Connection conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean incluir(T data) {
        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            PreparedStatement statement = incluirStatement(conn, data);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean excluir(int id) {
        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            PreparedStatement statement = excluirStatement(conn, id);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean alterar(T data) {
        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            PreparedStatement statement = alterarStatement(conn, data);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public abstract List<T> listarTodos(Connection conn);
    public abstract PreparedStatement incluirStatement(Connection conn, T data);
    public abstract PreparedStatement excluirStatement(Connection conn, int id);
    public abstract PreparedStatement alterarStatement(Connection conn, T data);
}