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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class ServidorService {

    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();

    public ServidorService() {
        try {
            serverSocket = new ServerSocket(5555);

            while (true) {
                socket = serverSocket.accept();

                new Thread(new ListenerSocket(socket)).start();
            }

        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void send(ChatMenssage menssage, ObjectOutputStream output) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class ListenerSocket implements Runnable {

        private ObjectOutputStream output;
        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.output = new ObjectOutputStream(socket.getOutputStream());
                this.input = new ObjectInputStream(socket.getInputStream());
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
                        }
                    } else if (action.equals(Action.DISCONNCT)) {
                        disconnect(menssage, output);
                    } else if (action.equals(Action.SEND_ONE)) {

                    } else if (action.equals(Action.SEND_ALL)) {
                        sendAll(menssage);
                    } else if (action.equals(Action.USERS_ONLINE)) {

                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
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

        menssage.setText("Deixou a Sala");

        menssage.setAction(Action.SEND_ONE);

        sendAll(menssage);

        System.out.println("User" + menssage.getName() + " saiu da sala");
    }

    private void sendOne(ChatMenssage menssage, ObjectOutputStream output) {
        try {
            output.writeObject(menssage);
        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendAll(ChatMenssage menssage) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (!kv.getKey().equals(menssage.getName())) {
                menssage.setAction(Action.SEND_ONE);
                try {
                    kv.getValue().writeObject(menssage);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
