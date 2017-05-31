package services;

import java.util.ArrayList;
import services.javaServices.*;

/**
 *
 * @author heshan
 */
public class JavaServiceDirectory {

    
    static ArrayList<String> stringArgs = new ArrayList();
    static ArrayList<Integer> intArgs = new ArrayList();
    static ArrayList<Float> floatArgs = new ArrayList();
    static ArrayList<Long> longArgs = new ArrayList();
    static ArrayList<Double> doubleArgs = new ArrayList();
    static ArrayList<Character> charArgs = new ArrayList();
    
    
    public static String getService(int serviceID, ArrayList<String> argList, ArrayList<String> argTypeList){
        
        fillArgArrayLists(argList,argTypeList);
        String result = "";
        switch (serviceID){
            
            case 1:
                result = RabinKarp.run(stringArgs.get(0),stringArgs.get(1));
                break;
            
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
                    stringArgs.add(JavaConverter.convertToString(argList.get(i)));
                    break;
                case "integer":
                    intArgs.add(JavaConverter.convertToInteger(argList.get(i)));
                    break;
                case "float":
                    floatArgs.add(JavaConverter.convertToFloat(argList.get(i)));
                    break;
                case "double":
                    doubleArgs.add(JavaConverter.convertToDouble(argList.get(i)));
                    break;
                case "long":
                    longArgs.add(JavaConverter.convertToLong(argList.get(i)));
                    break;
                case "character":
                    charArgs.add(JavaConverter.convertToCharacter(argList.get(i)));
                    break;    
            }
            i++;
	}
    }
    
}
