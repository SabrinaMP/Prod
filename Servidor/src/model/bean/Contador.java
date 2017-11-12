/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

/**
 *
 * @author victor
 */
public class Contador {
    private int cd_usuario;
    private String nm_nick;
    private int nr_cont;

    /**
     * 
     * @param cd_usuario
     * @param nm_nick
     * @param nr_logs
     * @param nr_count_palavrao 
     */
    public Contador(){
        this.nm_nick = nm_nick;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    public String getNm_nick() {
        return nm_nick;
    }

    public void setNm_nick(String nm_nick) {
        this.nm_nick = nm_nick;
    }    

    public int getNr_cont() {
        return nr_cont;
    }

    public void setNr_cont(int nr_cont) {
        this.nr_cont = nr_cont;
    }
    
}
