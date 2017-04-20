/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3_client;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import pop3_client.Model.ContextSMTP;
import pop3_client.utils.utils;

/**
 *
 * @author yoannlathuiliere
 */
public class ConsoleModeFrame extends javax.swing.JDialog {

    public ConsoleModeFrame() {
        initComponents();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
    }

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        serverLogTextArea = new javax.swing.JTextArea();
        commandTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        serverLogTextArea.setColumns(20);
        serverLogTextArea.setRows(5);
        jScrollPane1.setViewportView(serverLogTextArea);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(commandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        
    }// </editor-fold>                        

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        String cmd = commandTextField.getText();
        
        if(!cmd.equals("")) {
            sendRequest(cmd);
        }
    }
    
    public void sendRequest(String command) {
        writeClientCommand(command);
        ContextSMTP.getInstance().sendCommand(command);
        
        String response = ContextSMTP.getInstance().receiveRep();
        writeServerResponse(response);
        writeServerResponse(command);
    }

    public void sendEndRequest() {
        ContextSMTP.getInstance().sendEndCommand();
    }
    
    public void writeServerResponse(String response) {
        serverLogTextArea.append("[SERVER] : " + response + "\n\n\n");
    }
    
    public void writeClientCommand(String msg) {
        serverLogTextArea.append("[CLIENT] : " + msg + "\n\n\n");
    }

    // Variables declaration - do not modify                     
    private javax.swing.JTextField commandTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea serverLogTextArea;
    // End of variables declaration        
}
