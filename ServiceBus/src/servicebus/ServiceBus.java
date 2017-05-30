package servicebus;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author heshan
 */
public class ServiceBus implements Runnable{

    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;
    private static BufferedReader inputLine = null;
    private static boolean closed = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        socketConnection(5000,"localhost");
        
    }
    
    public static void socketConnection(int portNumber,String host){
        
        try{
            clientSocket = new Socket(host,portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServiceBus.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if ( clientSocket != null && os != null && is != null){
            try{
                // new thread to read from the server
                new Thread(new ServiceBus()).start();
                while (!closed){
                    os.println(inputLine.readLine());
                }
                os.close();
                is.close();
                clientSocket.close();
                
            } catch (IOException e){
                System.err.println("IOEXception: "+e);
            }
        }
    
    }

    @Override
    public void run() {
        
        String responseLine;
        try{
            while ((responseLine = is.readLine()) != null){
                System.out.println(responseLine);
            }
            closed = true;
        } catch (IOException e){
            System.err.println("IOExcpetion: "+e);
        }
    }

    
    
}
