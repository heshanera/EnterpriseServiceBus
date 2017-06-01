/* 
 * File:   Fibonacci.cpp
 * Author: heshan
 * 
 * Created on June 1, 2017, 9:46 PM
 */

#include "Fibonacci.h"

Fibonacci::Fibonacci() {}

Fibonacci::Fibonacci(const Fibonacci& orig) {}

Fibonacci::~Fibonacci() {}


std::string Fibonacci::run(int val){
    int result = fib(40);
    std::string strResult = std::to_string(result);
    return strResult;
}


int Fibonacci::fib(int n){
    int tmp[n+1];
    tmp[0] = 0;
    tmp[1] = 1;
    for (int i=2; i < n+1; i++){
        tmp[i] = -1;
     }
    int ans = calcfib(n,tmp);
    return ans;
}

int Fibonacci::calcfib(int n, int *p){
    if (n == 0){
        return p[0];
    } else if (n == 1){
        return p[1];
    } else {
        int tmp;
        if (p[n] != -1) tmp = p[n];
        else {
            tmp = calcfib(n-1,p) + calcfib(n-2,p);
            p[n] = tmp;
        }
        return tmp; 
    }	
}
