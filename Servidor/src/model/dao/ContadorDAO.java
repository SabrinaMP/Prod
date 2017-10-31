/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bean.Contador;

/**
 *
 * @author victor
 *
 * Classe Data Access Object Realiza as 4 operações CRUD no banco de dados
 * criar, ler, Atualizar e excluir Data: 18/09/2017
 *
 */
public class ContadorDAO {

    // atributo de conexão
    private Connection con = null;

    public ContadorDAO() {
        con = ConnectionFactory.getConnection();

    }

    //cadastro produto  
    public boolean save(Contador contador) {
        String sql = "INSERT INTO contador(nm_nick,nr_logs,nr_count_palavrao) VALUES (?,?,?)";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(2, contador.getNm_nick());
            stmt.setInt(3, contador.getNr_logs());
            stmt.setInt(4, contador.getNr_count_palavrao());
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao cadastrar produto: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }    

    /**
     * Método que atualiza um produto do banco de dados
     *
     * @param contador (Objeto)
     * @return boolean
     */

    public boolean atualizar(Contador contador) {
        String sql = "UPDATE contador SET nr_nick = ?, nr_logs = ?, nr_count_palavrao = ? WHERE cd_usuario=?";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt = con.prepareStatement(sql);
            stmt.setString(2, contador.getNm_nick());
            stmt.setInt(3, contador.getNr_logs());
            stmt.setInt(4, contador.getNr_count_palavrao());
            stmt.setInt(4, contador.getCd_usuario());
            stmt.executeUpdate();
            

            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao excluir produto: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
}
