package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author heshan
 */
public class Services implements Runnable{
    
    Socket socket;
    static HashMap<Integer,Socket>socketMap = new HashMap<Integer,Socket>();
    static int k = 1;
    
    Services(Socket socket){
        this.socket = socket;
    }
    
    public static void main(String args[]) throws IOException{
        
        ServerSocket ssock = new ServerSocket(5000);
        System.out.println("Listening");
        
        while (true){
            
            Socket sock = ssock.accept();
            Services service = new Services(sock);
            new Thread(service).start();
            socketMap.put(k,sock);
            System.out.println("Connected to the client"+k);
            k++;
        }
        
    }

    @Override
    public void run() {
        try{
            PrintWriter out;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            String j = "sendmsg";
            String l = "to";
            String t = "sendtoall";
            
            while ((inputLine = in.readLine()) != null ){
                String a[] = inputLine.split(" ");
                if ( a[0].equals(j) && a[2].equals(l) ){
                    
                    int id = Integer.parseInt(a[3]);
                    if (socketMap.containsKey(id)){
                        Socket service = socketMap.get(id);
                        out = new PrintWriter(service.getOutputStream(), true);
                        out.println(a[1]);
                        out.flush();
                    } else {
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("User Offline");
                        out.flush();
                    }
                    
                } else if ( a[0].equals(t) ) {
                    
                    for(int h =1; h < socketMap.size(); h++){
                        Socket service = socketMap.get(h);

                        out = new PrintWriter(service.getOutputStream(), true);
                        out.println(a[1]);
                        out.flush();
                    }
                    
                } else {
                    
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Invalid Message");
                    out.flush();
                        
                }
            }
        } catch (IOException e) {
            System.err.println("IOEXception: "+e);
        }   
        
    }
    
}
