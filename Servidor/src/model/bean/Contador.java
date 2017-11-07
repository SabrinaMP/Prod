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


    public Contador(int cd_usuario, String nm_nick, int nr_logs, int nr_count_palavrao) {
        this.cd_usuario = cd_usuario;
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
}
