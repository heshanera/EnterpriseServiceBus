def BruteForceMatch(T,P):
    result1 = "No Match found";
    result2 = "";
    match = False;
    i = 0           
    for i in range(len(T)-len(P)+1):    
        j = 0
        for j in range(len(P)):
            if T[i+j] != P[j]:          
                break
            else:
                if j == len(P)-1:
                    if match == True:
                        result2 += "    "
                    match = True
                    #print "match is found at" , i+1 ,"to ", i+j+1 ,"(letter no of the text)"
                    result2 += "match is found from " + str(i+1) + " to " + str(i+j+1)
                    i += j
    if (match == False):
        return result1;                
    else:
        return result2;            
            
def run( text, pattern ):
    return BruteForceMatch(text,pattern)
