
package model.bean;

/**
 *
 * @author victor
 * getter e setter para inserção de dados do banco 
 */
public class Contador {

    private String nm_nick;

    public Contador(String nm_nick) {
        this.nm_nick = nm_nick;
    }

    public String getNm_nick() {
        return nm_nick;
    }

    public void setNm_nick(String nm_nick) {
        this.nm_nick = nm_nick;
    }
}
