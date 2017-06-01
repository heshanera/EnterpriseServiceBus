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
    
    
    const char* name = "heshan";
    
    jstring result = env->NewStringUTF(name);

    return result;    
}


/*public void convert(jobjectArray argList, jobjectArray argTypeList){


}*/