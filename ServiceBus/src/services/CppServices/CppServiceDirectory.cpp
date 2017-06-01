#include <jni.h>
#include <iostream>
#include "CppServiceDirectory.h"
#include "Converter.h"
#include "Fibonacci.h"

JNIEXPORT jstring JNICALL 
Java_services_CppServiceDirectory_callNativeMethod(JNIEnv* env, jobject obj, jint serviceID, jobjectArray argList, jobjectArray argTypeList) {
    
    
    std::string stringResult;
    
    /******* Converting arguments ********************************************************/
    
    std::string *stringArgs = new std::string[10];
    int intArgs[10];
    float floatArgs[10];
    double doubleArgs[10];
    long longArgs[10];
    char charArgs[10];
    
    Converter converter(env);
    int argumentArrayPointers[] = {0,0,0,0,0,0};
    int noOfargs = env->GetArrayLength(argList);
    
    for(int k = 0; k < noOfargs; k++){
        
        jstring argType = (jstring)env->GetObjectArrayElement(argTypeList, k);
        jstring arg = (jstring)env->GetObjectArrayElement(argList, k);
        
        std::string stringArgType = converter.jstringTostring(argType);
        std::string stringArg = converter.jstringTostring(arg);
        
        
        if (stringArgType == "string") {
                stringArgs[argumentArrayPointers[0]] = converter.convertToString(stringArg);
                argumentArrayPointers[0] += 1;
                break;
        } else if (stringArgType == "integer") {
                intArgs[argumentArrayPointers[1]] = converter.convertToInteger(stringArg);
                argumentArrayPointers[1] += 1;
                break;
        } else if (stringArgType == "float") {
                floatArgs[argumentArrayPointers[2]] = converter.convertToFloat(stringArg);
                argumentArrayPointers[2] += 1;
                break;
        } else if (stringArgType == "double") {
                doubleArgs[argumentArrayPointers[3]] = converter.convertToDouble(stringArg);
                argumentArrayPointers[3] += 1;
                break;
        } else if (stringArgType == "long") {
                longArgs[argumentArrayPointers[4]] = converter.convertToLong(stringArg);
                argumentArrayPointers[4] += 1;
                break;
        } else if (stringArgType == "character") {
                charArgs[argumentArrayPointers[5]] = converter.convertToCharacter(stringArg);
                argumentArrayPointers[5] += 1;
                break;          
        }
    }
    
    switch (serviceID){
        
        case 3:
            Fibonacci fib;
            stringResult = fib.run(intArgs[0]);
            break;
    
    }
    
     
    const char * resultCharArr = stringResult.c_str();
    return env->NewStringUTF(resultCharArr);
}

