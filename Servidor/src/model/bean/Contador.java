/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

/**
 *
 * @author victo
 */
public class Contador {
    private int cd_usuario;
    private String nm_nick;
    private int nr_logs;
    private int nr_count_palavrao;


    public Contador(int cd_usuario, String nm_nick, int nr_logs, int nr_count_palavrao) {
        this.cd_usuario = cd_usuario;
        this.nm_nick = nm_nick;
        this.nr_logs = nr_logs;
        this.nr_count_palavrao = nr_count_palavrao;
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

    public int getNr_logs() {
        return nr_logs;
    }

    public void setNr_logs(int nr_logs) {
        this.nr_logs = nr_logs;
    }

    public int getNr_count_palavrao() {
        return nr_count_palavrao;
    }

    public void setNr_count_palavrao(int nr_count_palavrao) {
        this.nr_count_palavrao = nr_count_palavrao;
    }
    
}
