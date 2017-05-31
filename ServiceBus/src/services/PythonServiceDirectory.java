package services;

import java.util.ArrayList;
import org.python.core.PyInstance;  
import org.python.util.PythonInterpreter;  


/**
 *
 * @author heshan
 */
public class PythonServiceDirectory {

    public static String getService(int serviceID, ArrayList<String> argList, ArrayList<String> argTypeList) {
        
        String result = "";
        
        PythonServiceDirectory ie = new PythonServiceDirectory();  
        ie.execfile("src/services/PythonServices/hello.py");  
        PyInstance hello = ie.createClass("Hello", "None");  
        hello.invoke("run");
        
        return result;
    }
    
    PythonInterpreter interpreter = null;  


   public PythonServiceDirectory() {  
      PythonInterpreter.initialize(System.getProperties(), System.getProperties(), new String[0]);  
      this.interpreter = new PythonInterpreter();  
   }  

   void execfile( final String fileName ) {  
      this.interpreter.execfile(fileName);  
   }  

   PyInstance createClass( final String className, final String opts ) {  
      return (PyInstance) this.interpreter.eval(className + "(" + opts + ")");  
   }  
    
}
