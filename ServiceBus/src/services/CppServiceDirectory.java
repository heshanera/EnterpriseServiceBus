package services;

import java.util.ArrayList;

/**
 *
 * @author heshan
 */
public class CppServiceDirectory {
    
    public static String getService(int serviceID, ArrayList<String> argList, ArrayList<String> argTypeList){
        
        String result = "";
        
        new CppServiceDirectory().nativePrint();
        
        return result;
    }

    private native void nativePrint();
    
}
