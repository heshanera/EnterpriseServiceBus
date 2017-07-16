/* 
 * File:   Converter.cpp
 * Author: heshan
 * 
 * Created on June 1, 2017, 9:22 PM
 */

#include <iostream>
#include <vector>
#include <string>
#include "Converter.h"

Converter::Converter(JNIEnv* env) {
    this->env = env;
}

Converter::Converter(const Converter& orig) { }

Converter::~Converter() { }

jstring Converter::stringTojstring(std::string string){
    const char * resultCharArr = string.c_str();
    return env->NewStringUTF(resultCharArr);
}

std::string Converter::jstringTostring(jstring jStr ){

    if (!jStr) return "";
    std::vector<char> charsCode;
    const jchar *chars = env->GetStringChars(jStr, NULL);
    jsize len = env->GetStringLength(jStr);
    jsize i;
    for( i=0;i<len; i++){
        int code = (int)chars[i];
        charsCode.push_back( code );
    }
    env->ReleaseStringChars(jStr, chars);
    return  std::string(charsCode.begin(), charsCode.end());
}
    
std::string Converter::convertToString(std::string string){
    return string;
}

int Converter::convertToInteger(std::string val){ 
    return std::stoi( val );
}

float Converter::convertToFloat(std::string val){
    return ::atof(val.c_str());
}

double Converter::convertToDouble(std::string val){
    return ::atof(val.c_str());
}

long Converter::convertToLong(std::string val){
    return std::stol( val );
}

char Converter::convertToCharacter(std::string val){
    const char *cstr = val.c_str();
    return cstr[0];
}
