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
    public boolean save() {
        String sql = "INSERT INTO contador(nr_cont) VALUES (0)";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            //stmt.setString(1, contador.getNm_nick());
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao cadastrar: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
   
    public void update() {
        String sql = "UPDATE contador SET nr_cont = nr_cont + 1 WHERE cd_usuario = 1;";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.println("teste testado");
        } catch (SQLException ex) {
            System.err.println("Erro d0: " + ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    public void delete(){
        String sql = "DELETE  FROM contador WHERE cd_usuario > 1";
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
            //return true;
        } catch (SQLException ex) {
            System.err.println("Erro d1: " + ex);
            //return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    public void buscar(){
        String sql = "SELECT nr_cont FROM contador WHERE cd_usuario = 1;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareCall(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Contador cont = new Contador();
                cont.setNr_cont(rs.getInt("nr_cont"));
                System.out.println("treste: "+cont.getNr_cont());
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println("Erro d2: " + ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
}
