def BruteForceMatch(T,P):
    result = "No Match found";
    i = 0			
    for i in range(len(T)-len(P)+1):	
        j = 0
        for j in range(len(P)):
            if T[i+j] != P[j]:			
                break
            else:
                if j == len(P)-1:
                    #print "match is found at" , i+1 ,"to ", i+j+1 ,"(letter no of the text)"
                    result += "match is found at" , i+1 ,"to ", i+j+1 ,"(letter no of the text)\n"
                    i += j
    return result;            
			
def run( text, pattern ):
    return BruteForceMatch(text,pattern)
