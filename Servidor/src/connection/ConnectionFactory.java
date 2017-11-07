/*
 * Classe que realiza a conexão com o banco de dados MySQL
 */
package connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Guilher D
 * @author Kevin S
 * @author Luan J
 * @author Sabrina M
 * @author Victor B
 * 
 * @version 1
 * 
 * 
 */
public class ConnectionFactory {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/secret";
    private static final String USER = "root";
    private static final String PASS = "";
    
    /**
     * Faz a conexão com o banco de dados
     * @return caso ocorra erro, retorna uma mensagem de erro de conexão
     */
    public static Connection getConnection(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na conexão: " + ex);
        }
    }
    //Fechar a Conexão
    /**
     * Fecha a conexão com o banco de dados
     * @param con 
     */
    public static void closeConnection(Connection con){
       if(con != null) {
           try {
               con.close();
           } catch (SQLException ex) {
               System.err.println("Erro ao fechar a conexão: " + ex);
           }
       }
    }
    /**
     * Fecha conexão com Prepared Statement
     * @param con
     * @param stmt 
     */
    //Fechar a Conexão com Prepared Statement
    public static void closeConnection(Connection con, PreparedStatement stmt){
       if(stmt != null) {
           try {
               stmt.close();
           } catch (SQLException ex) {
               System.err.println("Erro ao fechar PreparedStatement: " + ex);
           }
       }
        closeConnection(con);
    }
    /**
     * Fecha conexão com o ResultSet
     * @param con
     * @param stmt
     * @param rs 
     */
    //Fechando ResultSet
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs){
       if(rs != null) {
           try {
               rs.close();
           } catch (SQLException ex) {
               System.err.println("Erro ao fechar ResultSet: " + ex);
           }
       }
        closeConnection(con,stmt);
    }
    
}
