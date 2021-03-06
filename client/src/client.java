
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Muhammad Izzuddin
 */
public class client extends javax.swing.JFrame implements WindowListener,
                                            WindowFocusListener,
                                            WindowStateListener {

    private Socket server = null;
    private BufferedOutputStream bos = null;
    private BufferedInputStream bis = null;
    private ObjectOutputStream oos  = null;
    private ObjectInputStream ois  = null;
    private FileInputStream fis = null;
    private FileOutputStream fos = null;
    private String destination = "d:/";
    private File f;
    private ArrayList<String> rcpt = new ArrayList<>();
    private boolean connectFlag = false;
    /**
     * Creates new form client
     */
    public client() {
        initComponents();
        this.setEnabling(false);
        progStats.setVisible(false);
        addWindowListener(new java.awt.event.WindowAdapter()   
        {  
            @Override
            public void windowClosing( java.awt.event.WindowEvent e )   
            {  
                dispose();
                if(connectFlag == true){
                    System.out.println("GOOD BYE");
                    disconnectFrom();
                }
                System.exit( 0 );  
            }  
        });  
        
    }
    
    private void connectTo()
    {
        try {
            if ((uname.getText().equals("") || ipserver.getText().equals(""))){
                throw new Exception("Silahkan isi username dan alamat server terlebih dahulu",null);
            }
            server = new Socket(ipserver.getText(),6060);
            bos = new BufferedOutputStream(server.getOutputStream());
            oos = new ObjectOutputStream(server.getOutputStream());
            ois = new ObjectInputStream(server.getInputStream());
            uname.setEnabled(false);
            this.setEnabling(true);
            oos.writeUTF(uname.getText());
            oos.flush();

            String msg;
            msg = ois.readUTF();
            if(msg.equals("EXIST")){
                //server.close();
                this.setEnabling(false);
                conn.setEnabled(true);
                uname.setEnabled(true);
                connectFlag = false;
                System.out.println(uname.getText() + " EXIST");
                throw new Exception("Username '"+uname.getText()+"' sudah ada dan belum terdiskonek dari server",null);
            }
            File file = new File("D:/" + uname.getText());
            if (!file.exists()) {
                if (file.mkdir()) {
                        System.out.println("Directory is created!");
                } else {
                        System.out.println("Failed to create directory!");
                }
            }
            System.out.println(msg);
            changeTitle("Simple File Sharing (Client :" + uname.getText() +")");
            connectFlag = true;
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Server sedang offline");
            connectFlag = false;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
            connectFlag = false;
        }
    }
    
    private void disconnectFrom(){
        try {
            oos.writeUTF("QUIT");
            oos.flush();
            server.close();
            this.setEnabling(false);
            conn.setEnabled(true);
            uname.setEnabled(true);
            connectFlag = false;
        } catch (IOException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void changeTitle(String newTitle)
    {
        this.setTitle(newTitle);
    }
    private void setEnabling(boolean param){
        rcptList.setEnabled(param);
        sRcptList.setEnabled(param); 
        pathFile.setEnabled(param);
        rfshBtn.setEnabled(param);
        addBtn.setEnabled(param);
        sendFile.setEnabled(param);
        attachBtn.setEnabled(param);
        inbox.setEnabled(param);
        rtrvBtn.setEnabled(param);
        rfshInbox.setEnabled(param);
        rtrvBtn.setEnabled(param);
        clrBtn.setEnabled(param);
        progCancel.setEnabled(param);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        conn = new javax.swing.JButton();
        attachBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        sRcptList = new javax.swing.JTextArea();
        pathFile = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        uname = new java.awt.TextField();
        sendFile = new javax.swing.JButton();
        rcptList = new javax.swing.JComboBox();
        addBtn = new javax.swing.JButton();
        rfshBtn = new javax.swing.JButton();
        clrBtn = new javax.swing.JButton();
        rtrvBtn = new javax.swing.JButton();
        inbox = new javax.swing.JComboBox();
        rfshInbox = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        ipserver = new java.awt.TextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        progStatsBar = new javax.swing.JProgressBar();
        progStats = new javax.swing.JLabel();
        progCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple File Sharing (Client)");
        setResizable(false);

        jLabel1.setText("Recipient List");

        jLabel2.setText("Username");

        conn.setText("Connect");
        conn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connActionPerformed(evt);
            }
        });

        attachBtn.setText("Browse File");
        attachBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachBtnActionPerformed(evt);
            }
        });

        sRcptList.setEditable(false);
        sRcptList.setColumns(1);
        sRcptList.setLineWrap(true);
        sRcptList.setRows(2);
        sRcptList.setWrapStyleWord(true);
        sRcptList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setViewportView(sRcptList);

        pathFile.setEditable(false);

        jLabel3.setText("Path of File");

        uname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unameActionPerformed(evt);
            }
        });

        sendFile.setText("Send File");
        sendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFileActionPerformed(evt);
            }
        });

        addBtn.setText("Add");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        rfshBtn.setText("Refresh");
        rfshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfshBtnActionPerformed(evt);
            }
        });

        clrBtn.setText("Clear List");
        clrBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clrBtnActionPerformed(evt);
            }
        });

        rtrvBtn.setText("Retrieve");
        rtrvBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rtrvBtnActionPerformed(evt);
            }
        });

        rfshInbox.setText("Refresh Inbox");
        rfshInbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfshInboxActionPerformed(evt);
            }
        });

        jLabel4.setText("Inbox File List");

        ipserver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipserverActionPerformed(evt);
            }
        });

        jLabel5.setText("IP Server");

        jLabel6.setText("Set Up Connection");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        progStatsBar.setToolTipText("");

        progStats.setText("Sending...");

        progCancel.setText("Cancel");
        progCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(attachBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendFile, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(ipserver, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                    .addComponent(uname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jLabel6)
                                .addComponent(conn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)
                                .addComponent(rcptList, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rfshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(clrBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(inbox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(rfshInbox, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(rtrvBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jSeparator2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(progCancel)
                            .addGap(1, 1, 1))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(progStats))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(pathFile, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(progStatsBar, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rcptList, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(addBtn)
                                    .addComponent(rfshBtn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clrBtn))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(5, 5, 5)
                                .addComponent(uname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel5)
                                .addGap(1, 1, 1)
                                .addComponent(ipserver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(conn, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(progStats)
                            .addComponent(progStatsBar, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(progCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pathFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(attachBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rfshInbox))
                    .addComponent(rtrvBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connActionPerformed
        //progStatsBar.setValue(25);
        if(conn.getText().equals("Connect")){
            connectTo();
            if(connectFlag == true)
                conn.setText("Disconnect");
        }
        else{
            disconnectFrom();
            if(connectFlag == false)
                conn.setText("Connect");
        }
        
    }//GEN-LAST:event_connActionPerformed

    private void attachBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachBtnActionPerformed
        JFileChooser jc = new JFileChooser();
        jc.showOpenDialog(null);
        f = jc.getSelectedFile();
        if (f != null)
            pathFile.setText(f.getAbsolutePath());
    }//GEN-LAST:event_attachBtnActionPerformed

    private void unameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unameActionPerformed

    private void sendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFileActionPerformed
        try {
            if (f == null || rcpt.isEmpty()){
                throw new Exception("Pastikan Anda Sudah Memilih File dan Memilih Recipient",null);
            }
            oos.writeUTF("RCPT");
            oos.flush();
            
            String msg = ois.readUTF();
            if (msg.equals("OK")){
                ListString Baru = new ListString();
                Baru.setNames(rcpt);
                oos.writeObject(Baru);
                oos.flush();
                oos.reset();
            }
            msg = ois.readUTF();
            if (msg.equals("FILE")){
                progStats.setVisible(true);
                ContentFile MyFile = new ContentFile(((int)f.length()));
                MyFile.setNamafile(f.getName());
                MyFile.setUkuran((int)f.length());
                byte [] mybyte = new byte[(int)f.length()];
                
                fis = new FileInputStream(f);
                bis = new BufferedInputStream(fis);
                //bis.read(mybyte,0,mybyte.length);
                MyFile.setByteFile(mybyte);
                //System.out.print(MyFile.getUkuran());
                int prog = 0;
                byte[] buffer = new byte[5]; 
                long bytesRead = 0;
                while ((prog = bis.read(buffer, 0, buffer.length)) != -1){
                    
                    //oos.write(buffer, 0, prog);             // write to the file     
                    bytesRead += prog;
                    int copyProgress = (int) (bytesRead*100.0/bis.available());
                    progStatsBar.setValue(copyProgress);           // update the indicator
                    //if (cancel) return;                  // kill the thread
                    System.out.println(copyProgress);
                }                
                
                oos.writeObject(MyFile);
                oos.flush();
                oos.reset();
                bis.close();
                fis.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//GEN-LAST:event_sendFileActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        try{
            if (rcptList.getSelectedItem() == null)
                throw new Exception("Silahkan pilih recipient terlebih dahulu",null);
            sRcptList.append(rcptList.getSelectedItem().toString() + "\n");
            rcpt.add(rcptList.getSelectedItem().toString());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void rfshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfshBtnActionPerformed
        try {
            oos.writeUTF("LIST");
            oos.flush();
            ListString Baru = new ListString();
            Baru = (ListString) ois.readObject();
            ArrayList<String> names = Baru.getNames();
            rcptList.removeAllItems();
            for (int i = 0; i < names.size();i++){
                rcptList.addItem(names.get(i));
            }
        } catch (IOException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rfshBtnActionPerformed

    private void clrBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clrBtnActionPerformed
        sRcptList.setText(null);
        rcpt.clear();
    }//GEN-LAST:event_clrBtnActionPerformed

    private void rtrvBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rtrvBtnActionPerformed
        ContentFile MyFile;
        try {
            if (inbox.getSelectedItem()== null)
                throw new Exception("Anda belum memilih file yang akan di download",null);
            oos.writeUTF("RETR");
            oos.flush();
            String msg = ois.readUTF();
            if (msg.equals("OK")){
                oos.writeUTF(inbox.getSelectedItem().toString());
                oos.flush();
                MyFile = (ContentFile) ois.readObject();
                System.out.println(MyFile.getNamafile());
                byte [] mybytearray  = new byte [MyFile.getUkuran()+30000];
                System.out.println(MyFile.getUkuran());
                mybytearray = MyFile.getByteFile();
                File f = new File(destination + "/" + uname.getText() + "/" + MyFile.getNamafile());
                int i = 0;
                while (f.exists() == true){
                    i++;
                    f = new File(destination + "/" + uname.getText() + "/" + i + "_" + MyFile.getNamafile());
                    System.out.println(i);
                }
                if (i==0)
                    destination += "/" + uname.getText() + "/" + MyFile.getNamafile();
                else 
                    destination += "/" + uname.getText() + "/" + i + "_" + MyFile.getNamafile();
                fos = new FileOutputStream(destination);
                bos = new BufferedOutputStream(fos);
                bos.write(mybytearray, 0 , MyFile.getUkuran());
                bos.flush();
                destination = "d:/";
                bos.close();
                fos.close();
                inbox.removeItem(inbox.getSelectedItem());
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//GEN-LAST:event_rtrvBtnActionPerformed

    private void rfshInboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfshInboxActionPerformed
        try {
            oos.writeUTF("INBOX");
            oos.flush();
            ListString Baru = new ListString();
            Baru = (ListString) ois.readObject();
            ArrayList<String> names = Baru.getNames();
            inbox.removeAllItems();
            for (int i = 0; i < names.size();i++){
                inbox.addItem(names.get(i));
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rfshInboxActionPerformed

    private void ipserverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipserverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipserverActionPerformed

    private void progCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_progCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_progCancelActionPerformed

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
            java.util.logging.Logger.getLogger(client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton attachBtn;
    private javax.swing.JButton clrBtn;
    private javax.swing.JButton conn;
    private javax.swing.JComboBox inbox;
    private java.awt.TextField ipserver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField pathFile;
    private javax.swing.JButton progCancel;
    private javax.swing.JLabel progStats;
    private javax.swing.JProgressBar progStatsBar;
    private javax.swing.JComboBox rcptList;
    private javax.swing.JButton rfshBtn;
    private javax.swing.JButton rfshInbox;
    private javax.swing.JButton rtrvBtn;
    private javax.swing.JTextArea sRcptList;
    private javax.swing.JButton sendFile;
    private java.awt.TextField uname;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
