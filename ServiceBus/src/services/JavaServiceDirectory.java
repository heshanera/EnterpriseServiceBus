package services;

import java.util.ArrayList;
import services.javaServices.*;

/**
 *
 * @author heshan
 */
public class JavaServiceDirectory {

    
    
    public static String getService(int serviceID, ArrayList<String> argList, ArrayList<String> argTypeList){
        
        String result = "";
        switch (serviceID){
            
            case 1:
                result = RabinKarp.run(argList.get(1),argList.get(2));
                break;
            
        }
        return result;
    }
    
}
