package servicebus;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
import services.CppServiceDirectory;
import services.JavaServiceDirectory;
import services.PythonServiceDirectory;

/**
 *
 * @author heshan
 */
public class ServiceBus implements Runnable{
    
    Socket clientSocket;
    int ClientID;
    
    static ArrayList<Socket> serviceSocketList;
    static ArrayList<Integer> requestIDList = new ArrayList<>();
    
    static HashMap<Integer,Socket> clientSocketMap = new HashMap<Integer,Socket>();
    static HashMap<String,Service> serviceMap = new HashMap<String,Service>();
    static boolean closed = false;
    
    static int k = 1;
    
    public ServiceBus(Socket socket, int clientID){
        this.clientSocket = socket;
        this.ClientID = clientID;
    }
    
    public static void main(String args[]){
        
        loadServiceMap(); // loading the meta data about the services
        startListening(); // start listening to the client requests
        
    }
    
    public static void startListening(){
        try {
            ServerSocket ssock = new ServerSocket(5001);
            System.out.println("Service Bus Listening...");
            
            while (true){
                
                Socket sock = ssock.accept();
                ServiceBus service = new ServiceBus(sock,k);
                new Thread(service).start();                
                System.out.println("*** Connected to the client"+k); k++;
            }
        } catch (IOException ex) {
            //Logger.getLogger(ServiceBus.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("port '5001' is already in use! Please free the port and start the ServiceBus again");
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
            
            // Loads the data in EsbEnvConfig.xml file
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
            String serviceInfo;
            
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
                    argType = argType.toLowerCase();
                    arguments.add(argType);
                }
                
                deadLetterDestination =  childNodeList.item(9).getTextContent();
                authConstraint =  childNodeList.item(11).getTextContent();
                retryPolicy =  childNodeList.item(13).getTextContent();
                serviceInfo = childNodeList.item(15).getTextContent();
                
                service.setServiceName(serviceName);
                service.setServiceId(serviceId);
                service.setServiceType(serviceType);
                service.setArguments(arguments);
                service.setDeadLetterDestination(deadLetterDestination);
                service.setAuthConstraint(authConstraint);
                service.setRetryPolicy(retryPolicy);
                service.setInfo(serviceInfo);
                
                serviceMap.put(serviceName, service);
                
            }
            
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            Logger.getLogger(ServiceBus.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static int createRequestID(String serviceName){
        
        int requestID;
        Random random;
        while(true){
            random = new Random();
            requestID = random.nextInt(50) + 1;
            if ( !requestIDList.contains(requestID) ) {
                requestIDList.add(requestID);
                break;
            }
        } 
        return requestID; 
    }
    
    @Override
    public void run() {
        try{
            PrintWriter out;
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            //String t = "sendtoall";
            
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.isEmpty()) {
                    String messageParts[] = inputLine.split(" ");
                    String serviceName = messageParts[0];
                    if (serviceMap.containsKey(serviceName)) {
                        
                        int requestId = createRequestID(serviceName);
                        clientSocketMap.put( this.ClientID,clientSocket);
                        Service service = serviceMap.get(serviceName);
                        
                        System.out.println("----------------------------------------------\n");
                        
                        System.out.println("Request Info: ");
                        System.out.println("Service Name: " + serviceName);
                        System.out.println("Client ID: " + this.ClientID);
                        System.out.println("Request ID: " + requestId);
                        
                        System.out.println("\n----------------------------------------------");
                        
                        
                        // check for the argument validity
                        if (messageParts.length == service.getArguments().size() +1 ){
                        
                            int serviceID = service.getServiceId();
                            String type = service.getServiceType().toLowerCase();
                            ArrayList<String> argTypeList = service.getArguments();
                            ArrayList<String> argList = new ArrayList<>(Arrays.asList(messageParts));
                            
                            String result = "";
                            
                            switch (type){
                                
                                case "java":
                                    result = JavaServiceDirectory.getService(serviceID,argList,argTypeList);
                                    break;
                                            
                                case "cpp":
                                    result = CppServiceDirectory.getService(serviceID,argList,argTypeList);
                                    break;
                                    
                                case "python":
                                    result = PythonServiceDirectory.getService(serviceID,argList);
                                    break;
                            
                            }
                            

                            out = new PrintWriter(clientSocket.getOutputStream(), true);
                            out.println("----------------------------------------------\n");
                            out.println("Result:\n" + result);
                            out.println("\n----------------------------------------------");
                            out.flush();
                            requestIDList.remove((Integer)requestId);
                        
                        } else {
                        
                            String msg = "SYNTAX ERROR | " + serviceName;
 
                            for(int j = 1; j < service.getArguments().size()+1; j++){
                                msg += " arg" + j;
                            }
                            out = new PrintWriter(clientSocket.getOutputStream(), true);
                            out.println("----------------------------------------------\n");
                            out.println(msg);
                            out.println("\n----------------------------------------------");
                            out.flush();
                            
                        }
                        
                        
                        
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
                                out.println("----------------------------------------------\n");
                                out.println("Services: \n");
                                Object seriviceArray[] = serviceMap.keySet().toArray();
                                String serviceInfo = "";
                                for(Object service : seriviceArray){
                                    
                                    int i = 1;
                                    serviceInfo = (String)service;
                                    for (String argument : serviceMap.get(serviceInfo).getArguments()) {
                                        serviceInfo += " <param"+i+">"; i++;
                                    }
                                    out.println(serviceInfo);
                                }   
                                out.println("\n----------------------------------------------");
                                out.flush();
                                break;
                            case "exit":
                                out = new PrintWriter(clientSocket.getOutputStream(), true);
                                out.println("_exit");
                                out.flush();
                                break;
                            case "man":
                                out = new PrintWriter(clientSocket.getOutputStream(), true);
                                if (messageParts.length == 2){
                                    
                                    if (serviceMap.containsKey(messageParts[1])){
                                        
                                        Service service = serviceMap.get(messageParts[1]);
                                        out.println("----------------------------------------------\n");
                                        out.println(service.getInfo());
                                        int i = 1;
                                        serviceInfo = messageParts[1];
                                        for (String argument : serviceMap.get(serviceInfo).getArguments()) {
                                            serviceInfo += " <param"+i+">"; i++;
                                        }
                                        out.println("SYNTAX: " + serviceInfo);
                                        
                                        
                                        out.println("\n----------------------------------------------");
                                        
                                    }
                                    
                                } else {
                                    out.println("----------------------------------------------\n");
                                    out.println("Commands: ");
                                    out.println("man: return the list of commands");
                                    out.println("man <serviceName>: return the service info");
                                    out.println("list: return the list of services");
                                    out.println("exit: disconnect from the Service Bus");
                                    out.println("\n----------------------------------------------");
                                }    
                                break;    
                            default:
                                out = new PrintWriter(clientSocket.getOutputStream(), true);
                                out.println("INVALID COMMAND");
                                out.println("PLEASE ENTER 'man' TO GET THE LIST OF COMMANDS");
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
