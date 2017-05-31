package services;

import java.util.ArrayList;
import java.util.HashMap;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;  


/**
 *
 * @author heshan
 */
public class PythonServiceDirectory {

    public static String getService(int serviceID, ArrayList<String> argList) {
        
        String result;
        HashMap<Integer,String> pythonFileMap = new HashMap<>();
        pythonFileMap.put(2, "Calculator.py");
      
        result = run(pythonFileMap.get(serviceID),argList);  
        return result;
    }
    
    public static String run(String PythonFile,ArrayList<String> argList){
        
        argList.remove(0);
        String argsList = "";
        argsList = argList.stream().map((arg) -> ("'"+arg + "',")).reduce(argsList, String::concat);
        argsList = argsList.substring(0, argsList.length()-1);
        
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("src/services/PythonServices/Calculator.py");
        PyObject result = interpreter.eval("repr(run("+argsList+"))");
        return result.toString();
    }
    
    
}
