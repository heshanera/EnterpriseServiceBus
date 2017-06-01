#include <jni.h>
#include <iostream>
#include "CppServiceDirectory.h"

JNIEXPORT jstring JNICALL 
Java_services_CppServiceDirectory_callNativeMethod(JNIEnv* env, jobject obj, jint serviceID, jobjectArray argList, jobjectArray argTypeList) {
    
    
    //std::string stringResult;
    //jstring jstringResult;
    
    /******* Converting arguments ********************************************************/
    /*
    std::string *stringArgs = new std::string[10];
    int *intArgs = new int[10];
    float *floatArgs = new float[10];
    double *doubleArgs = new double[10];
    long *longArgs = new long[10];
    char *charArgs = new char[10];
    
    Converter converter(env);
    int argumentArrayPointers[] = {0,0,0,0,0,0};
    int noOfargs = env->GetArrayLength(argList);
    
    for(int k = 0; k < noOfargs; k++){
        
        jstring argType = (jstring)env->GetObjectArrayElement(argTypeList, k);
        
        if (argType == (jstring)"string") {
                stringArgs[argumentArrayPointers[0]] = converter.convertToString(argType);
                argumentArrayPointers[0] += 1;
                break;
        } else if (argType == (jstring)"integer") {
                intArgs[argumentArrayPointers[1]] = converter.convertToInteger(argType);
                argumentArrayPointers[1] += 1;
                break;
        } else if (argType == (jstring)"float") {
                floatArgs[argumentArrayPointers[2]] = converter.convertToFloat(argType);
                argumentArrayPointers[2] += 1;
                break;
        } else if (argType == (jstring)"double") {
                doubleArgs[argumentArrayPointers[3]] = converter.convertToDouble(argType);
                argumentArrayPointers[3] += 1;
                break;
        } else if (argType == (jstring)"long") {
                longArgs[argumentArrayPointers[4]] = converter.convertToLong(argType);
                argumentArrayPointers[4] += 1;
                break;
        } else if (argType == (jstring)"character") {
                charArgs[argumentArrayPointers[5]] = converter.convertToCharacter(argType);
                argumentArrayPointers[5] += 1;
                break;    
        }
    }
    */
    
    /*
    switch (serviceID){
        
        case 3:
            stringResult = "heshan"; //  call the cpp method
            break;
    
    }
    */
    //jstringResult = (jstring)"heshan";//converter.stringTojstring(stringResult);
    return (jstring)"heshan"; //jstringResult;    
}

