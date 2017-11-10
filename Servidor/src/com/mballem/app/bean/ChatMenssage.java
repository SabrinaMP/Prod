/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mballem.app.bean;

import java.awt.Desktop.Action;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Guilher D
 * @author Kevin S
 * @author Luan J
 * @author Sabrina M
 * @author Victor B
 * 
 * @version 1 
 * construtor, getters e setters
 */
public class ChatMenssage implements Serializable {
    private String name;
    private String text;
    private String nameReserved;
    private Set<String> setOnlines = new HashSet<String>();
    private int caount; 
    private Action action; 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNameReserved() {
        return nameReserved;
    }

    public void setNameReserved(String nameReserved) {
        this.nameReserved = nameReserved;
    }

    public Set<String> getSetOnlines() {
        return setOnlines;
    }

    public void setSetOnlines(Set<String> setOnlines) {
        this.setOnlines = setOnlines;
    }

    public int getCaount() {
        return caount;
    }

    public void setCaount(int caount) {
        this.caount = caount;
    }
    

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
    
    public enum Action {
        CONNECT, DISCONNCT, SEND_ONE, SEND_ALL, USERS_ONLINE
    }
}
