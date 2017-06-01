package services;

import java.util.ArrayList;
import services.javaServices.*;

/**
 *
 * @author heshan
 */
public class JavaServiceDirectory {

    
    static ArrayList<String> stringArgs = new ArrayList<>();
    static ArrayList<Integer> intArgs = new ArrayList<>();
    static ArrayList<Float> floatArgs = new ArrayList<>();
    static ArrayList<Long> longArgs = new ArrayList<>();
    static ArrayList<Double> doubleArgs = new ArrayList<>();
    static ArrayList<Character> charArgs = new ArrayList<>();
    
    
    public static String getService(int serviceID, ArrayList<String> argList, ArrayList<String> argTypeList){
        
        fillArgArrayLists(argList,argTypeList);
        String result = "";
        switch (serviceID){
            
            /** Adding a new Service **/
            case 1:
                result = RabinKarp.run(stringArgs.get(0),stringArgs.get(1));
                break;
            /**
             * ex:
             * 
             * case <serviceID> :
             *      result = <ClassName>.run(<param1> , <param2>, <param3>, ...) 
             * 
             * 
             */    
                
            
        }
        return result;
    }
    
    public static void fillArgArrayLists(ArrayList<String> argList, ArrayList<String> argTypeList){
    
        stringArgs.clear(); intArgs.clear(); floatArgs.clear();
        doubleArgs.clear(); longArgs.clear(); charArgs.clear();
        
        int i = 1;
        for (String argType : argTypeList) {
            argType = argType.toLowerCase();
            switch(argType){
                case "string":
                    stringArgs.add(convertToString(argList.get(i)));
                    break;
                case "integer":
                    intArgs.add(convertToInteger(argList.get(i)));
                    break;
                case "float":
                    floatArgs.add(convertToFloat(argList.get(i)));
                    break;
                case "double":
                    doubleArgs.add(convertToDouble(argList.get(i)));
                    break;
                case "long":
                    longArgs.add(convertToLong(argList.get(i)));
                    break;
                case "character":
                    charArgs.add(convertToCharacter(argList.get(i)));
                    break;    
            }
            i++;
	}
    }
    
    public static String convertToString(String val){
        return val;
    }
    
    public static int convertToInteger(String val){
        return Integer.parseInt(val);
    }
    
    public static Float convertToFloat(String val){
        return Float.parseFloat(val);
    }
    
    public static Long convertToLong(String val){
        return Long.parseLong(val);
    }
    
    public static Double convertToDouble(String val){
        return Double.parseDouble(val);
    }
    
    public static char convertToCharacter(String val){
        return val.charAt(0);
    }
    
}
