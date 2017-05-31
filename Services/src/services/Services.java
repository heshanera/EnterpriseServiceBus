package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author heshan
 */
public class Services implements Runnable{
    
    Socket serviceSocket;
    
    public Services(Socket socket){
        this.serviceSocket = socket;
    }
    
    public static void main(String args[]) throws IOException{
        startListening();
    }

    @Override
    public void run() {
        
        try{
            PrintWriter out;
            BufferedReader in = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
            String inputLine;
            //String t = "sendtoall";
            
            System.out.println("asdkjashdkjashkjdhsakjd");
            
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.isEmpty()) {
                    String messageParts[] = inputLine.split(" ");
                    String serviceName = messageParts[0];
                    
                    System.out.println(serviceName);
                }    
            } 
            
            out = new PrintWriter(serviceSocket.getOutputStream(), true);
            out.println("PLEASE REQUEST A SERVICE");
            out.flush();
            
        } catch (IOException e) {
            System.err.println("IOEXception: "+e);
        }   
        
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
