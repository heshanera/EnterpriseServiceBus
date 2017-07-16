/* 
 * File:   Converter.h
 * Author: heshan
 *
 * Created on June 1, 2017, 9:22 PM
 */

#ifndef CONVERTER_H
#define CONVERTER_H

#include <jni.h>
#include <iostream>

class Converter {
public:
    Converter(JNIEnv* env);
    Converter(const Converter& orig);
    virtual ~Converter();
    
    jstring stringTojstring(std::string);
    std::string jstringTostring(jstring);
    
    std::string convertToString(std::string);
    int convertToInteger(std::string);
    float convertToFloat(std::string);
    double convertToDouble(std::string);
    long convertToLong(std::string);
    char convertToCharacter(std::string);
    
    
private:
    JNIEnv* env;

};

#endif /* CONVERTER_H */

