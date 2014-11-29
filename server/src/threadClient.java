/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muhammad Izzuddin
 */
public class threadClient implements Runnable{
    private Socket sockcli;
    private final ArrayList<threadClient> alThread;
    private BufferedOutputStream bos = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private final SocketAddress sa;
    private String username;
    private ContentFile MyFile;
    private ArrayList<ContentFile> Files = new ArrayList<>();
    public threadClient (Socket sockcli, ArrayList<threadClient> t){
        this.sockcli = sockcli;
        this.alThread = t;
        this.sa = this.sockcli.getRemoteSocketAddress();
    }
    @Override
    public void run(){
        try {
            dos = new DataOutputStream(this.getSockcli().getOutputStream());
            bos = new BufferedOutputStream(this.getSockcli().getOutputStream());
            oos = new ObjectOutputStream(this.getSockcli().getOutputStream());
            ois = new ObjectInputStream(this.getSockcli().getInputStream());
            dis = new DataInputStream(this.getSockcli().getInputStream());
            
            String msg;
            boolean isExist = false;
            msg = ois.readUTF();
            for(int i=0;i<this.alThread.size();i++){                
                if(msg.equals(this.alThread.get(i).getUsername())){
                    isExist = true;
                    break;
                }
            }
            
            if(isExist)
                throw new Exception("EXIST",null);
            
            oos.writeUTF("HALO");
            oos.flush();
            
            
            System.out.print(msg + "\n");
            this.setUsername(msg);
            
            while ((msg = ois.readUTF()) != null ){
                System.out.println(msg);
                if (msg.equals("LIST")){
                    this.sendList();
                }
                else if (msg.equals("QUIT")){
                    break;
                }
                else if (msg.equals("RCPT")){
                    oos.writeUTF("OK");
                    oos.flush();
                    
                    ListString Baru = new ListString();
                    Baru = (ListString) ois.readObject();
                    ArrayList<String> names = Baru.getNames();
                    for (int i=0;i<names.size();i++){
                        System.out.print(names.get(i));
                    }
                    System.out.print("\n");
                    
                    oos.writeUTF("FILE");
                    oos.flush();
                    MyFile = (ContentFile) ois.readObject();
                    sendMessage(MyFile, names);
                }
                else if (msg.equals("INBOX")){
                    ListString baru = new ListString();
                    for(int i = 0;i < Files.size();i++){
                        baru.getNames().add(Files.get(i).getNamafile());
                    }
                    oos.writeObject(baru);
                    oos.flush();
                    oos.reset();
                }
                else if (msg.equals("RETR")){
                    oos.writeUTF("OK");
                    oos.flush();
                    String fileName = ois.readUTF();
                    sendObject(Files, fileName);
                }
            }
            oos.close();
            getSockcli().close();
            synchronized(this.alThread){
                this.alThread.remove(this);
            }
        } catch (IOException ex) {
            Logger.getLogger(threadClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(threadClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            try {
                oos.writeUTF(e.getMessage());
                oos.flush();
            } catch (IOException ex) {
                Logger.getLogger(threadClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println(e.getMessage());
            //Logger.getLogger(threadClient.class.getName()).log(Level.SEVERE, null, e);
        }  
    }
    public synchronized void sendObject(ArrayList<ContentFile> Files, String names) throws IOException
    {
        System.out.println(names);
        for (int i=0;i <Files.size();i++){
            if (Files.get(i).getNamafile().equals(names)){
                ContentFile baru = Files.get(i);
                System.out.println(baru.getNamafile());
                oos.reset();
                oos.writeObject(baru);
                oos.flush();
                oos.reset();
                Files.remove(i);
            }
        }
    }
    public synchronized void sendMessage(Object obj, ArrayList<String> names) throws IOException
    {
        for (int i=0;i <this.alThread.size();i++){
            threadClient tc = this.alThread.get(i);
            for (int j=0; j < names.size();j++){
                if (tc.getUsername().equals(names.get(j))){
                    tc.getFiles().add((ContentFile) obj);
                }
            }
        }
    }
    public synchronized void sendList() throws IOException{
        ArrayList<String> names = new ArrayList<String>();
        threadClient tc = null;
        for(int i=0; i<this.alThread.size(); i++){
            tc  = this.alThread.get(i);
            //if(!tc.getUsername().equals(this.getUsername()))
                names.add(tc.getUsername());
        }
        ListString baru = new ListString();
        baru.setNames(names);
        oos.reset();
        oos.writeObject(baru);
        oos.flush();
        oos.reset();
    }
    /**
     * @return the sockcli
     */
    public Socket getSockcli() {
        return sockcli;
    }

    /**
     * @param sockcli the sockcli to set
     */
    public void setSockcli(Socket sockcli) {
        this.sockcli = sockcli;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the Files
     */
    public ArrayList<ContentFile> getFiles() {
        return Files;
    }

    /**
     * @param Files the Files to set
     */
    public void setFiles(ArrayList<ContentFile> Files) {
        this.Files = Files;
    }
}

