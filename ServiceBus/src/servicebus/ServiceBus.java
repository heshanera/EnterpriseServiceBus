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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author heshan
 */
public class ServiceBus implements Runnable{
    
    Socket socket;
    static HashMap<Integer,Socket>socketMap = new HashMap<Integer,Socket>();
    static HashMap<String,Service>serviceMap = new HashMap<String,Service>();
    static int k = 1;
    
    ServiceBus(Socket socket){
        this.socket = socket;
    }
    
    public static void main(String args[]){
        
        loadServiceMap(); // loading the meta data about the services
        startListening(); // start listening to the client requests
        
    }
    
    public static void startListening(){
        try {
            ServerSocket ssock = new ServerSocket(5000);
            System.out.println("Listening...");
            
            while (true){
                
                Socket sock = ssock.accept();
                ServiceBus service = new ServiceBus(sock);
                new Thread(service).start();
                socketMap.put(k,sock);
                System.out.println("Connected to the client"+k);
                k++;
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

    @Override
    public void run() {
        try{
            PrintWriter out;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            String t = "sendtoall";
            
            while ((inputLine = in.readLine()) != null ){
                
                if ( !inputLine.isEmpty() ){
                    
                    String messageParts[] = inputLine.split(" ");
                    
                    //int id = Integer.parseInt(messageParts[3]);
                    String serviceName = messageParts[0];
                    int serviceID = 1; // service id should be selected from a service Hash Map messageParts[0 ]
                    
                    if (socketMap.containsKey(serviceID)){
                        Socket service = socketMap.get(serviceID); // getting the service socket
                        out = new PrintWriter(service.getOutputStream(), true);
                        out.println("service called: " + serviceName);
                        out.flush();
                    } else {
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("Command " + messageParts[1] + " is called");
                        out.flush();
                    }
                    
                } /*else if ( messageParts[0].equals(t) ) {
                    
                    for(int h =1; h < socketMap.size(); h++){
                        Socket service = socketMap.get(h);

                        out = new PrintWriter(service.getOutputStream(), true);
                        out.println(messageParts[1]);
                        out.flush();
                    }
                    
                }*/ else {
                    
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("PLEASE REQUEST A SERVICE");
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.err.println("IOEXception: "+e);
        }   
        
    }
    
}
