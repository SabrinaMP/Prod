
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
 * @author Guilher D
 * @author Kevin S
 * @author Luan J
 * @author Sabrina M
 * @author Victor B
 * 
 * @version 1
 * 
 */ 
public class ContadorDAO {

    // atributo de conexão
    private Connection con = null;

    public ContadorDAO() {
        con = ConnectionFactory.getConnection();

    }

      /**
       * Salva as pessoas que já utilizaram o secret
       * @param contador
       * @return booleam
       */
    public boolean save(Contador contador) {
        String sql = "INSERT INTO contador(nm_nick) VALUES (?)";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, contador.getNm_nick());
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }    
    
}
