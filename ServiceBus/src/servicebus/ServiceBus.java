package servicebus;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author heshan
 */
public class ServiceBus implements Runnable{
    
    Socket clientSocket;
    static HashMap<Integer,Socket> clientSocketMap = new HashMap<Integer,Socket>();
    static ArrayList<Integer> clientIDList = new ArrayList<Integer>();
    static HashMap<Integer,String> clientRequestMap = new HashMap<Integer,String>();
    
    static HashMap<String,Service> serviceMap = new HashMap<String,Service>();
    
    
    static int k = 1;
    
    public ServiceBus(Socket socket){
        this.clientSocket = socket;
    }
    
    public static void main(String args[]){
        
        loadServiceMap(); // loading the meta data about the services
        startListening(); // start listening to the client requests
        
    }
    
    public static void startListening(){
        try {
            ServerSocket ssock = new ServerSocket(5000);
            System.out.println("Service Bus Listening...");
            
            while (true){
                
                Socket sock = ssock.accept();
                ServiceBus service = new ServiceBus(sock);
                new Thread(service).start();
                /*
                int clientId = createClientID("");
                clientSocketMap.put( clientId,sock);
                System.out.println(clientId);
                */
                
                
                //System.out.println("Connected to the client"+k);
                //k++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServiceBus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadServiceMap(){
        
        System.out.println("Loading service Meta data...");
        String configFilePath = "EsbConfiguration.xml";
        
        try {
            
            DocumentBuilderFactory factory;
            DocumentBuilder builder;
            Document document;
            Element rootElement;
            NodeList nodelist;
            
            // Loads the EsbEnvConfig.xml file
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(new File(configFilePath));
            rootElement = document.getDocumentElement();
            nodelist = rootElement.getElementsByTagName("Service");
            int services = nodelist.getLength();
            NodeList childNodeList = null;
            
            String serviceName;
            String serviceType;
            String deadLetterDestination;
            String authConstraint;
            String retryPolicy;
            int serviceId;
            ArrayList<String> arguments;
            
            for(int i = 0; i < services; i++)
            {
                arguments = new ArrayList<String>();
                Service service = new Service();
                
                childNodeList = nodelist.item(i).getChildNodes();
                
                serviceName =  childNodeList.item(1).getTextContent();
                serviceId =  Integer.parseInt(childNodeList.item(3).getTextContent());
                serviceType =  childNodeList.item(5).getTextContent();
                
                int noOfArguments =  Integer.parseInt(childNodeList.item(7).getChildNodes().item(1).getTextContent());
                for (int j = 0; j < noOfArguments; j++){
                    String argType = childNodeList.item(7).getChildNodes().item(3+(j*2)).getTextContent();
                    arguments.add(argType);
                }
                
                deadLetterDestination =  childNodeList.item(9).getTextContent();
                authConstraint =  childNodeList.item(11).getTextContent();
                retryPolicy =  childNodeList.item(13).getTextContent();
                
                service.setServiceName(serviceName);
                service.setServiceId(serviceId);
                service.setServiceType(serviceType);
                service.setArguments(arguments);
                service.setDeadLetterDestination(deadLetterDestination);
                service.setAuthConstraint(authConstraint);
                service.setRetryPolicy(retryPolicy);
                
                serviceMap.put(serviceName, service);
                
            }
            
            
            //serviceMap.put(serviceName, Service);
        } catch (SAXException ex) {
            Logger.getLogger(ServiceBus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ServiceBus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceBus.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static int createClientID(String serviceName){
        
        int clientID;
        Random random;
        while(true){
            random = new Random();
            clientID = random.nextInt(50) + 1;
            if ( !clientIDList.contains(clientID) ) {
                clientIDList.add(clientID);
                clientRequestMap.put(clientID, serviceName);
                break;
            }
        } 
        return clientID; 
    }
    
    
    @Override
    public void run() {
        try{
            PrintWriter out;
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            //String t = "sendtoall";
            
            OUTER:
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.isEmpty()) {
                    String messageParts[] = inputLine.split(" ");
                    String serviceName = messageParts[0];
                    if (serviceMap.containsKey(serviceName)) {
                        
                        System.out.println("Client x Request service " + serviceName);
                        
                        
                        int clientId = createClientID(serviceName);
                        clientSocketMap.put( clientId,clientSocket);
                        System.out.println(clientId);
                        
                        Service service = serviceMap.get(serviceName);
                        int serviceID = service.getServiceId();
                        /*
                        
                        requesting service
                        
                        return value
                        
                        */
                        String result = "return val from service";
                        
                        out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println(result + " " + serviceName);
                        out.flush();
                    } else {
                        
                        switch (serviceName) {
                            case "list":
                                //System.out.println(clientID);
                                /*
                                Socket serviceSocket = clientSocketMap.get(1L);
                                out = new PrintWriter(serviceSocket.getOutputStream(), true);
                                out.println();
                                out.flush();
                                */
                                
                                out = new PrintWriter(clientSocket.getOutputStream(), true);
                                out.println("----------------------------------------------");
                                out.println("Services: ");
                                Object seriviceArray[] = serviceMap.keySet().toArray();
                                for(Object service : seriviceArray){
                                    out.println((String)service);
                                }   
                                out.println("----------------------------------------------");
                                out.flush();
                                break;
                            case "exit":
                                out = new PrintWriter(clientSocket.getOutputStream(), true);
                                out.println("Bye");
                                out.flush();
                                break;
                            case "man":
                                out = new PrintWriter(clientSocket.getOutputStream(), true);
                                out.println("----------------------------------------------");
                                out.println("Commands: ");
                                out.println("list: return the list of service");
                                out.println("exit: disconnect from the service bus");
                                out.println("connect: connect to the service bus");
                                out.println("----------------------------------------------");
                                break;
                            default:
                                out = new PrintWriter(clientSocket.getOutputStream(), true);
                                out.println("INVALID COMMAND");
                                out.flush();
                                break;
                        }
                    }
                } else {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println("PLEASE REQUEST A SERVICE");
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.err.println("IOEXception: "+e);
        }   
        
    }
    
}
