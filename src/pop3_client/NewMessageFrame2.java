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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pop3_client.Model.ContextPOP3;
import pop3_client.Model.ContextSMTP;
import pop3_client.utils.utils;

/**
 *
 * @author yoannlathuiliere
 */
public class NewMessageFrame2 extends javax.swing.JDialog{
    
    private POP3ClientMainFrame mainFrame;
    
    public NewMessageFrame2(POP3ClientMainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        initComponents();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        recipientsTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (recipientsTextField.getText().equals("For multiple recipients, separate them with ';'")) {
                    recipientsTextField.setText("");
                    recipientsTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (recipientsTextField.getText().isEmpty()) {
                    recipientsTextField.setForeground(Color.GRAY);
                    recipientsTextField.setText("For multiple recipients, separate them with ';'");
                }    
            }
        });
        
        sendMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMailActionPerformed(evt);
            }
        });
    }
    
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        recipientsTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        objectTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        mailBodyTextArea = new javax.swing.JTextArea();
        sendMail = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("À");

        recipientsTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recipientsTextFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Object");

        mailBodyTextArea.setColumns(20);
        mailBodyTextArea.setRows(5);
        jScrollPane1.setViewportView(mailBodyTextArea);

        sendMail.setText("Send");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recipientsTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(objectTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sendMail)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(recipientsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(objectTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendMail)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void recipientsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:
    }   
    
    private void sendMailActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if (recipientsTextField.getText().equals("For multiple recipients, separate them with ';'") || recipientsTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter at least 2 recipients");
            return;
        }
        
        String[] recipientsTMP = recipientsTextField.getText().split(";");
        ArrayList<String> recipients = new ArrayList<String>();
                
        for(int i=0; i<recipientsTMP.length; i++) {
            if (!recipientsTMP[i].equals("") || !recipientsTMP[i].equals(" ")) {
                recipients.add(recipientsTMP[i]);
            }
        }
        
        if (recipients.size() < 2) {
            JOptionPane.showMessageDialog(this, "Please enter at least 2 recipients");
            return;
        }
        
        if (objectTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter an object");
            return;
        }
        
        launchMail(mainFrame.getUser());
    }

    public void launchMail(String from) {
        sendRequest("MAIL FROM: <" + from + ">");
        
        String response = ContextSMTP.getInstance().receiveRep();
        mainFrame.writeServerResponse(response);
    }
    
    public void sendRequest(String command) {
        mainFrame.writeClientCommand(command);
        ContextSMTP.getInstance().sendCommand(command);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewMessageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewMessageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewMessageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewMessageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewMessageFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea mailBodyTextArea;
    private javax.swing.JTextField objectTextField;
    private javax.swing.JTextField recipientsTextField;
    private javax.swing.JButton sendMail;
}
