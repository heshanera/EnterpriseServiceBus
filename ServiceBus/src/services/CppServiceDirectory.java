package services;

import java.util.ArrayList;

/**
 *
 * @author heshan
 */
public class CppServiceDirectory {
    
    static {
        System.load("/home/heshan/Projects/NetBeansProjects/EnterpriseServiceBus/ServiceBus/src/services/CppServices/dist/libCppServices.so");
    }
    
    
    public static String getService(int serviceID, ArrayList<String> argList, ArrayList<String> argTypeList){
        
        // converting argument ArrayList into string Array
        argList.remove(0); // removing the service name
        String[] argListArray = new String[argList.size()];
        argListArray = argList.toArray(argListArray);
        
        // converting argument types ArrayList into string Array
        String[] argTypeListArray = new String[argTypeList.size()];
        argTypeListArray = argList.toArray(argTypeListArray);
        
        // calling the native method
        CppServiceDirectory cppServiceDirectory = new CppServiceDirectory();
        String result = cppServiceDirectory.callNativeMethod(serviceID, argListArray, argTypeListArray);
            
        return result;
    }
    
    private native String callNativeMethod(int serviceID, String[] argList, String[] argTypeList);
    
}
