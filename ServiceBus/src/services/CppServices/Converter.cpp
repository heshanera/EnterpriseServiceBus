/* 
 * File:   Converter.cpp
 * Author: heshan
 * 
 * Created on June 1, 2017, 9:22 PM
 */

#include "Converter.h"

Converter::Converter(JNIEnv* env) {
    this->env = env;
}

Converter::Converter(const Converter& orig) { }

Converter::~Converter() { }

jstring Converter::stringTojstring(std::string string){
    return (jstring)"slakdj";
}

std::string Converter::jstringTostring(jstring string ){
    return "heshan";
    
}
    
std::string Converter::convertToString(jstring val){
    return "heshan";
}

int Converter::convertToInteger(jstring val){ 
    return 0;
}

float Converter::convertToFloat(jstring val){
    return 0.0;
}

double Converter::convertToDouble(jstring val){
    return 0.0;
}

long Converter::convertToLong(jstring val){
    return 0L;
}

char Converter::convertToCharacter(jstring val){
    return '0';
}
