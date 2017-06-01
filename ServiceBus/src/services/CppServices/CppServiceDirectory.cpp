#include <jni.h>
#include <iostream>
#include "CppServiceDirectory.h"

JNIEXPORT jstring JNICALL 
Java_services_CppServiceDirectory_callNativeMethod(JNIEnv* env, jobject obj, jint serviceID, jobjectArray argList, jobjectArray argTypeList) {
    
    /*
    std::cout<<serviceID;
    std::cout<<argList;+
    std::cout<<argTypeList;
    */
    
    /*
    int i, sum = 0;
    jsize len = (*env)->GetArrayLength(env, arr);
    */
    
    /******* Converting arguments ********************************************************/
    
    std::string *stringArgs = new std::string[10];
    int *intArgs = new int[10];
    float *floatArgs = new floa[10];
    long *longArgs = new long[10];
    double *doubleArgs = new double[10];
    char *charArgs = new char[10];
    
    int argumentArrayPointers[] = {0,0,0,0,0,0};
    
    int noOfargs = env->GetArrayLength(argList);
    
    for(int i = 0; i < noOfargs; i++){
        
        jstring argType = env->GetObjectArrayElement(argTypeList, i);
        switch(argType){
            case "string":

                argumentArrayPointers[0] += 1;
                break;
            case "integer":

                argumentArrayPointers[1] += 1;
                break;
            case "float":

                argumentArrayPointers[2] += 1;
                break;
            case "double":

                argumentArrayPointers[3] += 1;
                break;
            case "long":

                argumentArrayPointers[4] += 1;
                break;
            case "character":

                argumentArrayPointers[5] += 1;
                break;    
        }
    }
    
    
    
    
    
    jstring result = convert(env,obj,argList, argTypeList);

    return result;    
}

