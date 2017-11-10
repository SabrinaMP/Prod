/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mballem.app.service;

import com.mballem.app.bean.ChatMenssage;
import com.mballem.app.bean.ChatMenssage.Action;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Contador;
import model.dao.ContadorDAO;

/**
 * @author Guilher D
 * @author Kevin S
 * @author Luan J
 * @author Sabrina M
 * @author Victor B
 * 
 * @version 1
 * 
 * classe que faz conexão,desconexão, envio de mensagens para
 * todos e individual e visualização de usuarios onlines.
 * 
 * método Socket de conexão entre dois pontos. 
 * 
 */
public class ServidorService {
    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();
    
    public ServidorService() {
        try {
            serverSocket = new ServerSocket(8080);

            System.out.println("Servidor on!");

            while (true) {
                socket = serverSocket.accept();

                new Thread(new ListenerSocket(socket)).start();
            }

        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    private class ListenerSocket implements Runnable {
        private ObjectOutputStream output;
        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.output = new ObjectOutputStream(socket.getOutputStream());
                this.input = new ObjectInputStream (socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Override
        public void run() {
            ChatMenssage menssage = null;
            try {
                while ((menssage = (ChatMenssage) input.readObject()) != null) {
                    Action action = menssage.getAction();

                    if (action.equals(Action.CONNECT)) {
                        boolean isConnect = connect(menssage, output);
                        if (isConnect) {
                            mapOnlines.put(menssage.getName(), output);
                            sendOnlines();
                            Contador p = new Contador();
                            ContadorDAO dao = new ContadorDAO();
                            p.setNm_nick(menssage.getName());
                            dao.save(p);
                            System.out.println("Teste:"+p.getNm_nick());
                        }
                    } else if (action.equals(Action.DISCONNCT)) {
                        disconnect(menssage, output);
                        sendOnlines();
                        return;
                    } else if (action.equals(Action.SEND_ONE)) {
                        sendOne(menssage);
                    } else if (action.equals(Action.SEND_ALL)) {
                        sendAll(menssage);
                    }
                }
            } catch (IOException ex) {
                ChatMenssage cm = new ChatMenssage();
                cm.setName(menssage.getName());
                disconnect(cm, output);
                sendOnlines();
                System.out.println(menssage.getName() + " deixou o chat!");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
    private boolean connect(ChatMenssage menssage, ObjectOutputStream output) {
        if (mapOnlines.size() == 0) {
            menssage.setText("YES");
            send(menssage, output);
            return true;
        }

        if (mapOnlines.containsKey(menssage.getName())) {
            menssage.setText("NO");
            send(menssage, output);
            return false;
        } else {
            menssage.setText("YES");
            send(menssage, output);
            return true;
        }
    }
   

    private void disconnect(ChatMenssage menssage, ObjectOutputStream output) {
        mapOnlines.remove(menssage.getName());

        menssage.setText(" até logo!");

        menssage.setAction(Action.SEND_ONE);

        sendAll(menssage);

        System.out.println("User " + menssage.getName() + " sai da sala");
    }
   
    private void send(ChatMenssage message, ObjectOutputStream output) {
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private void sendOne(ChatMenssage menssage) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (kv.getKey().equals(menssage.getNameReserved())) {
                try {
                    kv.getValue().writeObject(menssage);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
   
    private void sendAll(ChatMenssage message) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (!kv.getKey().equals(message.getName())) {
                message.setAction(Action.SEND_ONE);
                try {
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
   
    private void sendOnlines() {
        Set<String> setNames = new HashSet<String>();
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            setNames.add(kv.getKey());
        }
        
        ChatMenssage menssage = new ChatMenssage();
        
        ContadorDAO cDAO = new ContadorDAO();
        int cont = 0;
        for(Contador c : cDAO.findAll()){
            cont += 1;
        }
        menssage.setCaount(cont);
        System.out.println(menssage.getCaount() + "//teste");
        
        menssage.setAction(Action.USERS_ONLINE);
        menssage.setSetOnlines(setNames);

        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            menssage.setName(kv.getKey());
            try {
                kv.getValue().writeObject(menssage);
            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
