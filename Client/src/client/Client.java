package client;

/**
 *
 * @author heshan
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable{

    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;
    private static BufferedReader inputLine = null;
    private static boolean closed = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        connectToServiceBus(5001,"localhost");
        
    }
    
    public static void connectToServiceBus(int portNumber,String host){
        
        try{
            clientSocket = new Socket(host,portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if ( clientSocket != null && os != null && is != null){
            try{
                // new thread to read from the server
                new Thread(new Client()).start();
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
                if (!responseLine.equals("_exit")){
                    System.out.println(responseLine);
                } else {
                    System.out.println("Bye!");
                    break;
                }
            }
            closed = true;
        } catch (IOException e){
            System.err.println("IOExcpetion: "+e);
        }
    }

    
    
}
