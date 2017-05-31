package services.javaServices;

/**
 *
 * @author heshan
 */
public class RabinKarp
{	
	public static long calcPatternHash(String def)
	{
		int length = def.length();
		long sVal = 0;
		for (int i = 0; i < length; i++)
		{
			sVal = ((int)(def.charAt(i)) + sVal)*3;
		}
		return sVal;
	}
	
	public static boolean match_deep(String p1, String p2)
	{
		int l = p1.length();
		boolean match = true;
		for (int i = 0; i < l; i++)
		{
			if (p1.charAt(i) != p2.charAt(i))
			{
				match = false;
				break;
			}
		}
		return match;
	}
	
	public static void match(String txt, String ptrn)
	{
		
		long patternHash = calcPatternHash(ptrn);
		long subStringHash = 0;
		int patternLength = ptrn.length();
		int textLength = txt.length();
		
		int end = patternLength - 1;
		
		for (int i = 0 ; i < (textLength-patternLength + 1); i++,end++)
		{
			String subString = txt.substring(i,end+1);
			subStringHash = calcPatternHash(subString);
			if (subStringHash == patternHash)
			{
				boolean matching = match_deep(subString, ptrn);
				if (matching)
				{
					System.out.println("Match found from " + i + " to " + end);
					i += (patternLength - 1);
					end += (patternLength - 1);
				}
			}
			
		}				 
	}
	
	
	public static String matchOverlaping(String txt, String ptrn)
	{
            String result = "";
            long patternHash = calcPatternHash(ptrn);
            long subStringHash = 0;
            int patternLength = ptrn.length();
            int textLength = txt.length();
            boolean noMatch = true;

            int end = patternLength - 1;

            for (int i = 0 ; i < (textLength-patternLength + 1); i++,end++)
            {
                String subString = txt.substring(i,end+1);
                subStringHash = calcPatternHash(subString);
                if (subStringHash == patternHash)
                {
                    boolean matching = match_deep(subString, ptrn);
                    if (matching)
                    {
                        //System.out.println("Match found from " + i + " to " + end);
                        result += "Match found from " + i + " to " + end + "\n";
                        noMatch = false;
                    }
                }
            }		
            if (noMatch == true) result = "No match found";
            return result;
	}
        
        public static String run(String text, String pattern){
        
            String result = matchOverlaping(text,pattern);
            return result;
        }
        
}	
