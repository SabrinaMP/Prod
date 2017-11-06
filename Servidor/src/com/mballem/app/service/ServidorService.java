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
            ChatMenssage message = null;
            try {
                while ((message = (ChatMenssage) input.readObject()) != null) {
                    Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        boolean isConnect = connect(message, output);
                        if (isConnect) {
                            mapOnlines.put(message.getName(), output);
                            sendOnlines();
                        }
                    } else if (action.equals(Action.DISCONNCT)) {
                        disconnect(message, output);
                        sendOnlines();
                        return;
                    } else if (action.equals(Action.SEND_ONE)) {
                        sendOne(message, output);
                    } else if (action.equals(Action.SEND_ALL)) {
                        sendAll(message);
                    }
                }
            } catch (IOException ex) {
                ChatMenssage cm = new ChatMenssage();
                cm.setName(message.getName());
                disconnect(cm, output);
                sendOnlines();
                System.out.println(message.getName() + " deixou o chat!");
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
    private void sendOnlines() {
        Set<String> setNames = new HashSet<String>();
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            setNames.add(kv.getKey());
        }

        ChatMenssage message = new ChatMenssage();
        message.setAction(Action.USERS_ONLINE);
        message.setSetOnlines(setNames);

        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            message.setName(kv.getKey());
            try {
                kv.getValue().writeObject(message);
            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
