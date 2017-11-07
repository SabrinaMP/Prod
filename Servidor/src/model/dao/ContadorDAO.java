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
    /**
     * 
     * @param contador
     * @return 
     */
   
    public boolean save(Contador contador) {
        String sql = "INSERT INTO contador(nm_nick) VALUES (?)";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(2, contador.getNm_nick());
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao cadastrar produto: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }    
    
}
