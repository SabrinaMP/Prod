/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mballem.app.frame;

import com.mballem.app.bean.ChatMenssage;
import com.mballem.app.bean.ChatMenssage.Action;
import com.mballem.app.service.ClienteService;
import java.awt.SystemColor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.bean.Contador;
import model.dao.ContadorDAO;

/**
 *
 * @author victor
 */
public class ClienteFrame extends javax.swing.JFrame {

    private Socket Socket;
    private ChatMenssage menssage;
    private ClienteService service;

    /**
     * Creates new form ServidorView
     */
    public ClienteFrame() {
        initComponents();
    }

    private class listenerSocket implements Runnable {
        private ObjectInputStream input;

        public listenerSocket(Socket socket) {
            try {
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            ChatMenssage message = null;
            try {
                while ((message = (ChatMenssage) input.readObject()) != null) {
                    Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        connect(message);
                    } else if (action.equals(Action.DISCONNCT)) {
                        disconnect();
                        Socket.close();
                    } else if (action.equals(Action.SEND_ONE)) {
                        System.out.println("::: " + message.getText() + " :::");
                        receive(message);
                    } else if (action.equals(Action.USERS_ONLINE)) {
                        refreshOnlines(message);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void connect(ChatMenssage menssage) {
        if (menssage.getText().equals("NO")) {
            this.txtName.setText("");
            JOptionPane.showMessageDialog(this, "Conexão não realizada!\nTente novamente com um novo nome.");
            return;
        }
        

        this.menssage = menssage;
        this.btnConectar.setEnabled(false);
        this.txtName.setEditable(false);

        this.btnSair.setEnabled(true);
        this.txtAreaSend.setEnabled(true);
        this.txtAreaReceive.setEnabled(true);
        this.btnEnviar.setEnabled(true);
        this.btnLimpar.setEnabled(true);

        JOptionPane.showMessageDialog(this, "Você está conectado no chat!");
        
        
        String nick = new String(txtName.getText());
        
        Contador p = new Contador(nick);
        ContadorDAO dao = new ContadorDAO();
        
        p.setNm_nick(nick);
    }

    private void disconnect() {
        this.btnConectar.setEnabled(true);
        this.txtName.setEditable(true);

        this.btnSair.setEnabled(false);
        this.txtAreaSend.setEnabled(false);
        this.txtAreaReceive.setEnabled(false);
        this.btnEnviar.setEnabled(false);
        this.btnLimpar.setEnabled(false);
        
        this.txtAreaReceive.setText("");
        this.txtAreaSend.setText("");

        JOptionPane.showMessageDialog(this, "Você saiu do chat!");
    }

    private void receive(ChatMenssage menssage) {
        this.txtAreaReceive.append(menssage.getName() + "diz :" + menssage.getText() + "\n");
    }

    private void refreshOnlines(ChatMenssage menssage) {
        
        System.out.println(menssage.getSetOnlines().toString());
        
        Set<String> names = menssage.getSetOnlines();
        
        names.remove(menssage.getName());
        
        String[] array = (String[]) names.toArray(new String[names.size()]);
        
        this.listOnlines.setListData(array);
        this.listOnlines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listOnlines.setLayoutOrientation(JList.VERTICAL);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        btnConectar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaReceive = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaSend = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        listOnlines = new javax.swing.JList<>();
        btnLimpar = new javax.swing.JButton();
        btnEnviar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secret");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        txtName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtName.setToolTipText("");
        txtName.setBorder(null);
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });

        btnConectar.setBackground(new java.awt.Color(0, 0, 0));
        btnConectar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnConectar.setForeground(new java.awt.Color(255, 255, 255));
        btnConectar.setText("CONNECT");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });

        btnSair.setBackground(new java.awt.Color(0, 0, 0));
        btnSair.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSair.setForeground(new java.awt.Color(255, 255, 255));
        btnSair.setText("EXIT");
        btnSair.setEnabled(false);
        btnSair.setPreferredSize(new java.awt.Dimension(77, 23));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        txtAreaReceive.setEditable(false);
        txtAreaReceive.setColumns(20);
        txtAreaReceive.setRows(5);
        txtAreaReceive.setBorder(null);
        jScrollPane1.setViewportView(txtAreaReceive);

        txtAreaSend.setColumns(20);
        txtAreaSend.setLineWrap(true);
        txtAreaSend.setRows(5);
        txtAreaSend.setBorder(null);
        txtAreaSend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAreaSendKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtAreaSend);

        listOnlines.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "--------" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listOnlines);

        btnLimpar.setBackground(new java.awt.Color(0, 0, 0));
        btnLimpar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLimpar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpar.setText("LIMPAR");
        btnLimpar.setEnabled(false);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnEnviar.setBackground(new java.awt.Color(0, 0, 0));
        btnEnviar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEnviar.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviar.setText("ENVIAR");
        btnEnviar.setEnabled(false);
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        btnEnviar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEnviarKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nick Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnConectar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(256, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(248, 248, 248))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConectar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEnviar)))
                .addGap(58, 58, 58))
        );

        txtName.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtAreaSendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaSendKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            //MÉTODO DO BOTÃO AQUI
            enter();
        }
    }//GEN-LAST:event_txtAreaSendKeyPressed

    private void btnEnviarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEnviarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            //MÉTODO DO BOTÃO AQUI
        }
    }//GEN-LAST:event_btnEnviarKeyPressed

    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        String name = this.txtName.getText();

        if (!name.isEmpty()) {
            this.menssage = new ChatMenssage();
            this.menssage.setAction(Action.CONNECT);
            this.menssage.setName(name);    

            this.service = new ClienteService();
            this.Socket = this.service.connect();

            new Thread(new listenerSocket(this.Socket)).start();
            btnEnviar.enable(true);
            btnLimpar.enable(true);
            btnSair.enable(true);
            
        }else{
            this.txtName.setText("");
            JOptionPane.showMessageDialog(this, "Conexão não realizada!\nDigite um Nickname.");
            return;
        }
        this.service.send(menssage);
    }//GEN-LAST:event_btnConectarActionPerformed
 
    public void enter(){
        String text = this.txtAreaSend.getText();
        String name = this.menssage.getName();
        
        this.menssage = new ChatMenssage();
        
        if (this.listOnlines.getSelectedIndex() > -1) {
            this.menssage.setNameReserved((String) this.listOnlines.getSelectedValue());
            this.menssage.setAction(Action.SEND_ONE);
            this.listOnlines.clearSelection();
        } else {
            this.menssage.setAction(Action.SEND_ALL);
        }
        
        if (!text.isEmpty()) {
            this.menssage.setName(name);
            this.menssage.setText(text);

            this.txtAreaReceive.append("Você disse: " + text + "\n");
            
            this.service.send(this.menssage);
        }
        
        this.txtAreaSend.setText("");
        
    }
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        ChatMenssage message = new ChatMenssage();
        message.setName(this.menssage.getName());
        message.setAction(Action.DISCONNCT);
        this.service.send(message);
        disconnect();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        this.txtAreaSend.setText("");
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        enter();
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNameFocusLost



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listOnlines;
    private javax.swing.JTextArea txtAreaReceive;
    private javax.swing.JTextArea txtAreaSend;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
