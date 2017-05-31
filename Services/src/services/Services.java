package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author heshan
 */
public class Services implements Runnable{
    
    Socket socket;
    
    public Services(Socket socket){
        this.socket = socket;
    }
    
    public static void main(String args[]) throws IOException{
                
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public static void startListening(){
        try {
            ServerSocket ssock = new ServerSocket(6000);
            System.out.println("Services Listening...");
            
            while (true){
                
                Socket sock = ssock.accept();
                Services service = new Services(sock);
                new Thread(service).start();
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
}
