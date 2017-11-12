/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mballem.app;

import com.mballem.app.service.ServidorService;
import model.bean.Contador;
import model.dao.ContadorDAO;

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
public class Servidor {

    /**
     * Inicializa o servidor
     * @param args os argumentos da linha de comando
     */
    public static void main(String[] args) {
        ContadorDAO dao = new ContadorDAO();
        dao.save();
        dao.delete();
        new ServidorService();
    }
    
}
