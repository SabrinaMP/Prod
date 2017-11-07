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
