package services;

/**
 *
 * @author heshan
 */
public class JavaConverter {
    
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
